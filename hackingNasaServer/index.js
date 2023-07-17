// khai báo package sử dụng
const express = require('express');
// thư viện đọc body request
const bodyParser = require('body-parser');
// thư viện quản lý resource
const cors = require('cors');
// thư viện kết nối database mysql
const mysql = require('mysql');
// sử dụng .env
require('dotenv').config();

// sử dụng express để dựng server nodejs
const app = express();
// sử dụng thư viện cors và bodyParser
app.use(cors({ origin: '*' }));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// tạo connection tới mysql
const conn = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT, // tuỳ mỗi máy - mặc định là 3306
    user: process.env.DB_USER, // tuỳ mỗi bạn (theo xampp) - thường mặc định là root
    password: process.env.DB_PASSWORD, // tuỳ máy mỗi người
    database: process.env.DB_NAME
});

// api upload ảnh
app.post('/upload', (req, res) => {
    // kiểm tra xem dữ liệu gửi lên đủ không
    if (!req.body.image
        || !req.body.title
        || !req.body.copyright
        || !req.body.date
        || !req.body.explaination) {
        res.json({ status: false, message: "Lack of information!" })
    } else {
        try {
            const image = req.body.image;
            const title = req.body.title;
            const copyright = req.body.copyright;
            const date = req.body.date;
            const explaination = req.body.explaination;

            // quert insert dữ liệu
            const sql = `INSERT INTO 
        ImageTable (title, image, copyright, date, explaination)
        VALUES
        ('${title}', '${image}', '${copyright}', '${date}', '${explaination}')`;
            conn.query(sql, (err, result) => {
                if (err) throw err;
                console.log("1 record has been inserted");
                res.json({ status: true, message: "1 record has been inserted" });
            });
        } catch (error) {
            res.json({ status: false, message: error });
        }
    }
});

// TODO: api /get-image
app.get('/get-image', (req, res) => {
    // TODO: tự làm
})

// TODO: api /delete-image
app.delete('/delete-image', (req, res) => {
    // TODO: tự làm
})

// TODO: api /update-image
app.put('/update-image', (req, res) => {
    // TODO: tự làm
})

// port server node chạy
const port = process.env.NODE_PORT;

app.listen(port, () => {
    console.log(`server is listening at http://localhost:${port}`);
})


