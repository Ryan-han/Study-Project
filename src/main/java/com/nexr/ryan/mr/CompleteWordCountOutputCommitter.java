package com.nexr.ryan.mr;

import java.io.IOException;

import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class CompleteWordCountOutputCommitter extends OutputCommitter {

	@Override
	public void setupJob(JobContext jobContext) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupTask(TaskAttemptContext taskContext) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean needsTaskCommit(TaskAttemptContext taskContext)
			throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void commitTask(TaskAttemptContext taskContext) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abortTask(TaskAttemptContext taskContext) throws IOException {
		// TODO Auto-generated method stub

	}

}
