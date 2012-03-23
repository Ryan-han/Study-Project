package com.nexr.ryan.buffer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExRecordParser {

	public static void main(String args[]) throws IOException,
			InterruptedException {

		File file = new File("/Users/ryan/Downloads/tomcat_log/catalina.out");
		File result = new File("/Users/ryan/Downloads/tomcat_log/result.out");

		RandomAccessFile raf = new RandomAccessFile(file, "r");
		// For reading data
		FileChannel in = raf.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(Short.MAX_VALUE);

		int rd = 0;
		while ((rd = in.read(buf)) > 0) {
			System.out.println("Before flip => Position : " + buf.position() + ", Limit: " + buf.limit() + ", Capacity: " + buf.capacity());
			buf.flip();
			extractLines(buf);
		}
	}

	static void extractLines(ByteBuffer buf) throws IOException,
			InterruptedException {
		
		System.out.println("After flip => Position : " + buf.position() + ", Limit: " + buf.limit() + ", Capacity: " + buf.capacity());
		int start = buf.position();
		buf.mark();
		StringBuilder eventBuilder = new StringBuilder();

		while (buf.hasRemaining()) {
			byte b = buf.get();
			// TODO windows: ('\r\n') line separators
			if (b == '\n') {
				int end = buf.position();
				int sz = end - start;
				byte[] body = new byte[sz - 1];
				buf.reset(); // go back to mark
				buf.get(body, 0, sz - 1); // read data
				buf.get(); // read '\n'
				buf.mark(); // new mark.
				start = buf.position();
				
				
				// Parttern으로 끝나 MultiEvent
//				Pattern p = Pattern.compile("deployDescriptor|start");
//				boolean isEnd = matchingParttern(p, new String(body));
//				
//				if (isEnd) {
//					eventBuilder.append(new String(body) + "\n");
//					if (eventBuilder.length() > 0) {
//						System.out.println(eventBuilder.substring(0,
//								eventBuilder.length() - 1).toString());
//						System.out.println("=========================================");
//						eventBuilder.delete(0, eventBuilder.length());
//					}
//				} else {
//					eventBuilder.append(new String(body) + "\n");
//				}
				
				
				
				// Parttern으로 시작해서 다음 Parttern전까지를 하나의 Event - MultiEvent
//				Pattern p = Pattern.compile("Sep|Jul");
//				Matcher m = p.matcher(new String(body));
//				boolean ismatch = m.find();
//		
//				if (ismatch && m.start() == 0) {
//					if (eventBuilder.length() > 0) {
//						System.out.println(eventBuilder.substring(0,
//								eventBuilder.length() - 1).toString());
//						System.out.println("=========================================");
//						eventBuilder.delete(0, eventBuilder.length());
//					}
//					eventBuilder.append(new String(body) + "\n");
//				} else {
//					eventBuilder.append(new String(body) + "\n");
//				}
			}
		}

		// rewind for any left overs
		buf.reset();
		buf.compact(); // shift leftovers to front.

	}
	
	static boolean result = false;
	public static boolean matchingParttern(Pattern p, String body){
		
		Matcher m = p.matcher(new String(body));
		boolean ismatch = m.find();
		if(ismatch){
			int endIdx = m.regionEnd();			
//			System.out.println("Body " + body);
//			System.out.println(m.end());
//			System.out.println(ismatch + " Start " + startIdx + " End " + endIdx + " length " + body.length());
			if(m.end() == endIdx){
//				System.out.println("success!!");
				result = true;
			}else{
					matchingParttern(p, new String(body).substring(m.end(), body.length()));
			}
		}else {
			result = false;
		}
		
		return result;
	}
	
}
