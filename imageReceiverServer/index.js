
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();
// get .env file to use as const variable
require('dotenv').config();
const port = 3000;

// get variable from .env file
const { DB_HOST, DB_USER, DB_PASS, DB_NAME } = process.env;

// use cors and body-parser middleware
app.use(cors({ origin: '*' }));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// create a connection to database ImageDataBase with mysql
const mysql = require('mysql');
const connection = mysql.createConnection({
    host: DB_HOST,
    user: DB_USER,
    password: DB_PASS,
    database: DB_NAME
});

// create a post route to save image to database
app.post('/upload', (req, res) => {

    // check if request body has image and name
    if (!req.body.image || !req.body.name) {
        // send response to client
        res.json({ status: false, message: "No image or name" });
    } else {
        try {
            // get image from request body
            const image = req.body.image;
            // create a sql query to insert image to database
            const sql = `INSERT INTO ImageDataBase (name, image) VALUES ('${req.body.name}', '${image}')`;
            // execute sql query
            connection.query(sql, (err, result) => {
                if (err) throw err;
                console.log("1 record inserted");
                // send response to client
                res.json({ status: true, message: "Image saved" });
            });
        } catch (error) {
            // send response to client
            res.json({ status: false, message: error });
        }
    }
});

// create a get route to get all images from database with name, if name is null, get all images
app.get('/get-images', (req, res) => {
    // check if request query has name
    if (!req.query.name) {
        // create a sql query to get all images from database
        const sql = `SELECT * FROM ImageDataBase`;
        // execute sql query
        connection.query(sql, (err, result) => {
            if (err) throw err;
            // send response to client
            res.json({ status: true, message: result });
        });
    } else {
        try {
            // get name from request query
            const name = req.query.name;
            // create a sql query to get all images from database with name
            const sql = `SELECT * FROM ImageDataBase WHERE name LIKE '%${name}%'`;
            // execute sql query
            connection.query(sql, (err, result) => {
                if (err) throw err;
                // send response to client
                res.json({ status: true, message: result });
            });
        } catch (error) {
            // send response to client
            res.json({ status: false, message: error });
        }
    }
});

// create a put route to update image with id or name
app.put('/update-image', (req, res) => {
    // check if request body has id or name
    if (!req.body.id && !req.body.name) {
        // send response to client
        res.json({ status: false, message: "No id or name" });
    } else {
        try {
            // get id and name from request body
            const id = req.body.id;
            const name = req.body.name;
            // create a sql query to update image with id or name
            const sql = `UPDATE ImageDataBase SET name = '${name}' WHERE id = ${id} OR name = '${name}'`;
            // execute sql query
            connection.query(sql, (err, result) => {
                if (err) throw err;
                console.log("1 record updated");
                // send response to client
                res.json({ status: true, message: "Image updated" });
            });
        } catch (error) {
            // send response to client
            res.json({ status: false, message: error });
        }
    }
});

// create a delete route to delete image with id or name
app.delete('/delete-image', (req, res) => {
    // check if request body has id or name
    if (!req.body.id && !req.body.name) {
        // send response to client
        res.json({ status: false, message: "No id or name" });
    } else {
        try {
            // get id and name from request body
            const id = req.body.id;
            const name = req.body.name;
            // create a sql query to delete image with id or name
            const sql = `DELETE FROM ImageDataBase WHERE id = ${id} OR name = '${name}'`;
            // execute sql query
            connection.query(sql, (err, result) => {
                if (err) throw err;
                console.log("1 record deleted");
                // send response to client
                res.json({ status: true, message: "Image deleted" });
            });
        } catch (error) {
            // send response to client
            res.json({ status: false, message: error });
        }
    }
});


app.listen(port, () => {
    console.log(`Image receiver server listening at http://localhost:${port}`);
});


