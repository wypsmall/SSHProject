package com.imti.sysmgr.servlet;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class RandomCode extends HttpServlet {
	private static final long serialVersionUID = 5039673797977591965L;
	public static final String RANDOM_CODE_VERIFY = "RANDOM_CODE_VERIFY";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		response.setContentType("image/GIF");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		int width = 60, height = 20;
		ServletOutputStream out = response.getOutputStream();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics gra = image.getGraphics();
		Random random = new Random();

		gra.setColor(getRandColor(200, 250));
		gra.fillRect(0, 0, width, height);
		gra.setColor(Color.black);
		gra.setFont(new Font("Times New Roman",Font.PLAIN,18));
		gra.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gra.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			gra.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110))); 
			gra.drawString(rand, 13 * i + 6, 16);
		}
		
		session.setAttribute(RANDOM_CODE_VERIFY, sRand);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	static Color getRandColor(int fc, int bc) { 
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void init() throws ServletException {
		// Put your code here
	}
}
