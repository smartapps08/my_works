package com.sa.pdftest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ContentByteUtils;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class PdfToText {

	public static void main(String[] args) {
		String src = "1341466060.pdf";
		String dest = "pages.txt";
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(dest));
			PdfReader reader = new PdfReader(src);

			RenderListener listener = new MyTextRenderListener(out);
			PdfContentStreamProcessor processor = new PdfContentStreamProcessor(
					listener);
			PdfDictionary pageDic = reader.getPageN(1);
			PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
			processor.processContent(
					ContentByteUtils.getContentBytesForPage(reader, 1),
					resourcesDic);
			out.flush();
			out.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class MyTextRenderListener implements RenderListener {

	/** The print writer to which the information will be written. */
	protected PrintWriter out;

	/**
	 * Creates a RenderListener that will look for text.
	 */
	public MyTextRenderListener(PrintWriter out) {
		this.out = out;
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
	 */
	public void beginTextBlock() {
		out.print("<");
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
	 */
	public void endTextBlock() {
		out.println(">");
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
	 */
	public void renderImage(ImageRenderInfo renderInfo) {
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#renderText(com.itextpdf.text.pdf.parser.TextRenderInfo)
	 */
	public void renderText(TextRenderInfo renderInfo) {
		out.print("<");
		String s = renderInfo.getText();
		out.print(s);
		System.out.print(s);

		out.print(">");
	}
}
