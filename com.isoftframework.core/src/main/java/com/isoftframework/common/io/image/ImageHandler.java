package com.isoftframework.common.io.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author wzq
 * @email 973598066@qq.com
 * @博客 http://tw-wangzhengquan.iteye.com/
 * @time: 2014-2-23 上午11:36:03
 */
public class ImageHandler {

	public static final String IMG_TYPE_BMP = "bmp";
	public static final String IMG_TYPE_GIF = "gif";
	public static final String IMG_TYPE_JPEG = "jpeg";
	public static final String IMG_TYPE_PNG = "png";
	public static final String IMG_TYPE_WBMP = "wbmp";
	
	private static ImageHandler instance=null;
	
	public static ImageHandler getInstance(){
		if(instance==null){
			instance=new ImageHandler();
		}
		return instance;
	}
	
	private ImageHandler(){}
	
	public BufferedImage readImage(InputStream src, String imgType)
			throws IOException {
		try {
			if (imgType != null
					&& (imgType.equalsIgnoreCase(IMG_TYPE_JPEG) )) {
				// 通过JPEG图像数据输入流创建JPEG数据流解码器
				JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(src);
				// 解码当前的JPEG数据流，返回BufferedImage对象
				return jpegDecoder.decodeAsBufferedImage();
			} else {
				return ImageIO.read(src);
			}
		} finally {
			src.close();
		}
	}

	public BufferedImage[] readImages(InputStream src, String imgType)
			throws IOException {
		try {
			ImageInputStream imageIn = ImageIO.createImageInputStream(src);
			ImageReader imageReader = null;
			Iterator<ImageReader> iter = ImageIO
					.getImageReadersByFormatName(imgType);
			if (iter.hasNext())
				imageReader = iter.next();
			imageReader.setInput(imageIn, true);
			List<BufferedImage> buffImgeList = new ArrayList<BufferedImage>();
			// System.out.println("thum count="+imageReader.getNumThumbnails(0));
			int i = 0;
			while (true) {
				try {
					buffImgeList.add(imageReader.read(i));
					i++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			return buffImgeList.toArray(new BufferedImage[] {});
		} finally {
			src.close();
		}

	}

	public void writeImage(BufferedImage buffImg, String imgType,
			OutputStream dest) throws IOException {
		if (imgType != null && imgType.equalsIgnoreCase(IMG_TYPE_JPEG)) {
			// 创建图形编码器，用于编码内存中的图像数据到JPEG数据输出流
			JPEGImageEncoder jpgEncoder = JPEGCodec.createJPEGEncoder(dest);
			// 编码BufferedImage对象到JPEG数据输出流
			jpgEncoder.encode(buffImg);
		} else {
			ImageIO.write(buffImg, imgType, dest);

		}
		dest.close();
	}

	public void writeImages(BufferedImage[] buffImges, String imgType,
			OutputStream dest) throws IOException {
		ImageWriter writer = null;
		Iterator<ImageWriter> iter = ImageIO
				.getImageWritersByFormatName(imgType);
		if (iter.hasNext())
			writer = iter.next();
		ImageOutputStream imgOut = ImageIO.createImageOutputStream(dest);
		writer.setOutput(imgOut);
		writer.write(new IIOImage(buffImges[0], null, null));
		for (int i = 1; i < buffImges.length; i++) {
			if (writer.canInsertImage(i)) {
				writer.writeInsert(i, new IIOImage(buffImges[i], null, null),
						null);
			}
		}
		dest.close();
	}

	public BufferedImage generateWatermarkImage(InputStream srcIn,
			String imgType, String markIcon, String markStr)
			throws ImageFormatException, IOException {
		return this.generateWatermarkImage(this.readImage(srcIn, imgType),
				markIcon, markStr);
	}

	public BufferedImage generateWatermarkImage(String srcFile,
			String markIcon, String markStr) throws ImageFormatException,
			IOException {
		return this.generateWatermarkImage(new ImageIcon(srcFile).getImage(),
				markIcon, markStr);
	}

	/**
	 * 给图片加水印
	 * 
	 * @param srcIn
	 * @param destOut
	 * @param markIcon
	 * @param markStr
	 * @throws ImageFormatException
	 * @throws IOException
	 */
	public BufferedImage generateWatermarkImage(Image srcImg, String markIcon,
			String markStr) throws ImageFormatException, IOException {

		BufferedImage buffImg = this.copy(srcImg);
		// BufferedImage buffImg=(BufferedImage) srcImg;
		Graphics2D g = buffImg.createGraphics();
		// 设置对线段的锯齿状边缘处理
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHints(hints);
		// Graphics2D g = buffImg.createGraphics();
		int width = buffImg.getWidth(null);
		int height = buffImg.getHeight(null);

		// 透明度
		float alpha = 0.6f;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		// 设置水印旋转
		g.rotate(Math.toRadians(10), (double) width / 2, (double) height / 2);

		if (markIcon != null && !markIcon.trim().equals("")) {
			// 创建ImageIcon对象，logo.gif作为水印图片
			ImageIcon imgIcon = new ImageIcon(markIcon);
			// 得到Image对象
			Image img = imgIcon.getImage();
			int imgWidth = img.getWidth(null);
			int imgHeight = img.getHeight(null);
			// 将水印绘制到图片上
			g.drawImage(img, (width - imgWidth) / 2, (height - imgHeight) / 2,
					null);
		}
		if (markStr != null && !markStr.trim().equals("")) {

			// 设置图形上下文的当前颜色为红色
			g.setColor(Color.GRAY);
			// g.setPaint(Color.GRAY);
			// 创建新的字体
			Font font = new Font("Courier New", Font.BOLD, 40);
			// 设置图形上下文的字体为指定的字体
			g.setFont(font);
			// 计算font居中位置
			int fontHeight = g.getFontMetrics().getHeight();
			int fontDesent = g.getFontMetrics().getDescent();
			int stringwidth = g.getFontMetrics().stringWidth(markStr);
			// Rectangle2D stringbounds=ig.getFontMetrics().getStringBounds(str,
			// g);

			int strxx = (width - stringwidth) / 2;
			int stryy = (height + fontHeight / 2 - fontDesent / 2) / 2;
			// 在图片上绘制文字，文字的颜色为图形上下文的当前颜色
			g.drawString(markStr, strxx, stryy);
		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		// 释放图形上下文使用的系统资源
		g.dispose();

		return buffImg;
	}

	public BufferedImage createGrayImage(InputStream srcIn) throws IOException {
		try {
			return this.createGrayImage(ImageIO.read(srcIn));
		} finally {
			srcIn.close();
		}

	}

	public BufferedImage createGrayImage(String srcFile) throws IOException {
		return this.createGrayImage(new ImageIcon(srcFile).getImage());
	}

	/**
	 * 图片变灰
	 * 
	 * @param srcIn
	 * @param destOut
	 * @throws IOException
	 */
	public BufferedImage createGrayImage(Image srcImg) throws IOException {

		BufferedImage buffImg = this.copy(srcImg);

		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp colorConvert = new ColorConvertOp(cs, cs, null);
		colorConvert.filter(buffImg, buffImg);
		return buffImg;
	}
	public BufferedImage copy(String srcFile, int destWidth)
			throws IOException {
		return this.copy(new ImageIcon(srcFile).getImage(), destWidth);
	}


	public BufferedImage copy(String srcFile, int destWidth, int destHeight)
			throws IOException {
		return this.copy(new ImageIcon(srcFile).getImage(), destWidth,
				destHeight);
	}
	public BufferedImage copy(InputStream srcIn, int destWidth)
			throws IOException {
		try {
			return this.copy(ImageIO.read(srcIn), destWidth);
		} finally {
			srcIn.close();
		}

	}

	public BufferedImage copy(InputStream srcIn, int destWidth, int destHeight)
			throws IOException {
		try {
			return this.copy(ImageIO.read(srcIn), destWidth, destHeight);
		} finally {
			srcIn.close();
		}

	}

	/**
	 * 复制图片
	 * 
	 * @param srcImg
	 * @return
	 * @throws IOException
	 */
	public BufferedImage copy(Image srcImg) throws IOException {
		return this.copy(srcImg, srcImg.getWidth(null), srcImg.getHeight(null));
	}
	public BufferedImage copy(Image srcImg, int destWidth) throws IOException{
		if(srcImg!=null){
			return this.copy(srcImg, destWidth, srcImg.getHeight(null)*destWidth/srcImg.getHeight(null));
		}else{
			return null;
		}
		
	}
	/**
	 * 复制图片 也可做缩放用
	 * 
	 * @param srcImg
	 * @param destWidth
	 * @param destHeight
	 * @return
	 * @throws IOException
	 */
	public BufferedImage copy(Image srcImg, int destWidth, int destHeight)
			throws IOException {
		// BufferedImage srcImg = ImageIO.read(srcIn);
		// 构造一个预定义的图像类型的BufferedImage对象
		BufferedImage buffImg = new BufferedImage(destWidth, destHeight,
				BufferedImage.TYPE_INT_RGB);
		// 创建Graphics2D对象，用于在BufferedImage上绘图
		Graphics2D g = buffImg.createGraphics();
		// 设置图形上下文的当前颜色为白色
		g.setColor(Color.WHITE);
		// 用图形上下文的当前颜色填充指定的矩形区域
		g.fillRect(0, 0, destWidth, destHeight);
		// 按照缩放的大小在BufferedImage对象上绘制原始图像
		g.drawImage(srcImg, 0, 0, destWidth, destHeight, null);
		// 释放图形上下文使用的系统资源
		g.dispose();
		return buffImg;
	}

	/**
	 * 缩放
	 * 
	 * @param srcFile
	 * @param sx
	 * @param sy
	 * @return
	 * @throws IOException
	 */
	public BufferedImage zoom(String srcFile, double sx, double sy)
			throws IOException {
		return this.zoom(new ImageIcon(srcFile).getImage(), sx, sy);

	}

	public BufferedImage zoom(Image srcImg, double s)
			throws IOException {
		return this.copy(srcImg, (int) (srcImg.getWidth(null) * s),
				(int) (srcImg.getHeight(null) * s));

	}
	public BufferedImage zoom(Image srcImg, double sx, double sy)
			throws IOException {
		return this.copy(srcImg, (int) (srcImg.getWidth(null) * sx),
				(int) (srcImg.getHeight(null) * sy));

	}

	public void test() throws ImageFormatException, FileNotFoundException,
			IOException {
		String imgType = "gif";
		String srcFile = "E:\\test\\icon.gif";
		String desFile = "E:\\test\\dest.PNG";
		
		// BufferedImage img=generateWatermarkImage(new FileInputStream(srcFile),imgType,"E:\\test\\log.png","http://iosftsotre");
		// BufferedImage
		// img=generateWatermarkImage((srcFile),"E:\\test\\log.png","http://iosftsotre");

		 BufferedImage img=this.copy((srcFile), 200, 100);

		// BufferedImage img=this.createGrayImage("E:\\test\\src.jpg");

		//BufferedImage img = this.zoom((srcFile), 2f, 2f);
		this.writeImage(img, imgType, new FileOutputStream(desFile));
		/*
		 * process2Gray(new FileInputStream(srcFile), new FileOutputStream(
		 * desFile),"jpg");
		 */
	}

	public static void main(String[] args) {
		ImageHandler imageHandler = new ImageHandler();
		try {
			imageHandler.test();
			System.out.println("finnished");
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
