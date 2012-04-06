package com.nexr.ryan.mr;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskID;

public class ComplexWordCountRecordWriter extends
		RecordWriter<Text, IntWritable> {
	
	private OutputStream out;
	private byte[] keyValueSeperator;
	

	public ComplexWordCountRecordWriter(Path outputPath,
			TaskAttemptContext context) throws IOException {
		// TODO Auto-generated constructor stub
		keyValueSeperator = "\t".getBytes("UTF-8");
		
		TaskID taskId = context.getTaskAttemptID().getTaskID();
		
		Path taskOutputFile = new Path(outputPath, "worldcount-result-" + taskId.getId());
		
		FileSystem fs = taskOutputFile.getFileSystem(context.getConfiguration());
		out = fs.create(taskOutputFile);
		
	}

	@Override
	public void write(Text key, IntWritable value) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		out.write(key.getBytes(), 0, key.getBytes().length);
		out.write(keyValueSeperator);
		out.write(value.toString().getBytes());
		out.write("\n".getBytes());
		
	}

	@Override
	public void close(TaskAttemptContext context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		out.close();
	}

}
