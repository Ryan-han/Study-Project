package com.nexr.ryan.mr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class ComplexWordCountLineRecordReader extends
		RecordReader<LongWritable, Text> {
	
	private long start;
	private long pos;
	private long end;
	private BufferedReader reader;
	private LongWritable key = null;
	private Text value = null;

	@Override
	public void initialize(InputSplit psplit, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileSplit split = (FileSplit) psplit;
		Configuration job = context.getConfiguration();
		this.start = split.getStart();
		this.end = split.getLength();
		final Path file = split.getPath();
		
		FileSystem fs = file.getFileSystem(job);
		this.reader = new BufferedReader(new InputStreamReader(fs.open(file)));
		this.pos = this.start;
		context.setStatus(file + " (" + start + " ~ " + end + ")" );
		 
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if (key == null)  {
			key = new LongWritable();
		}
		key.set(pos);
		
		if (value == null ) {
			value = new Text();
		}
		
		String line = reader.readLine();
		if(line == null) {
			key = null;
			value = null;
			return false;
		}
		
		pos += line.getBytes().length;
		if(pos >= end) {
			key = null;
			value = null; 
			return false;
		}
		
		value.set(line.getBytes("utf-8"));
		
		return true;
	}

	@Override
	public LongWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if(start == end) {
			return 0.0f;
		} else {
			return Math.min(1.0f, (pos - start) / (float) (end -start));
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		if (reader != null ) {
			reader.close();
		}
	}

}
