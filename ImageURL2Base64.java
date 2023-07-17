import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Base64;

public class ImageURL2Base64 {
    
    public static String convert(String url) throws Exception {
        URL imageUrl = new URL(url);
        // get image from url
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(imageUrl.openStream().readAllBytes());
        // encode image to base64
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        System.out.println(convert(url));
    }
    
}
