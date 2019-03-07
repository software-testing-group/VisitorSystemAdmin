package beike.visitorsystem.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class GenerateCaptcha {
	 
	    /*
	     * ����ַ��ֵ�
	     */
	    private final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
	        '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
	        'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	    
	    /*
	     * �����
	     */
	    private Random random = new Random();
	    
	    /*
	     * ��ȡ6λ�����
	     */
	    private String getRandomString()
	    {
	        StringBuffer buffer = new StringBuffer();
	        for(int i = 0; i < 6; i++)
	        {
	            buffer.append(CHARS[random.nextInt(CHARS.length)]);
	        }
	        return buffer.toString();
	    }
	    
	    /*
	     * ��ȡ�������ɫ
	     */
	    private Color getRandomColor()
	    {
	        return new Color(random.nextInt(255),random.nextInt(255),
	                random.nextInt(255));
	    }
	    
	    /*
	     * ����ĳ��ɫ�ķ�ɫ
	     */
	    private Color getReverseColor(Color c)
	    {
	        return new Color(255 - c.getRed(), 255 - c.getGreen(),
	                255 - c.getBlue());
	    }
	    
	    public void outputCaptcha(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException 
	    {

	        response.setContentType("image/jpeg");

	        String randomString = getRandomString();
	        request.getSession(true).setAttribute("randomString", randomString);

	        int width = 200;
	        int height = 60;

	        Color color = getRandomColor();
	        Color reverse = getReverseColor(color);

	        BufferedImage bi = new BufferedImage(width, height,
	                BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = bi.createGraphics();
	        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	        g.setColor(color);
	        g.fillRect(0, 0, width, height);
	        g.setColor(reverse);
	        g.drawString(randomString, 36, 40);
	        for (int i = 0, n = random.nextInt(100); i < n; i++) 
	        {
	            g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
	        }

	        // ת��JPEG��ʽ
	        ServletOutputStream out = response.getOutputStream();
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        encoder.encode(bi);
	        out.flush();
	    }
	
}
