package com.nexr.ryan.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ComplexWordCountPartitioner extends Partitioner<Text, IntWritable>{

	@Override
	public int getPartition(Text key, IntWritable value, int numPartitions) {
		// TODO Auto-generated method stub
		return (key.hashCode() & Integer.MAX_VALUE) % numPartitions;
	}

}
