package com.innowhite.PlaybackApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class TaskExecutorExample {

    private TaskExecutor taskExecutor;
    private PlaybackDataService playbackDataService;

    private static final Logger log = LoggerFactory.getLogger(TaskExecutorExample.class);
    
    public TaskExecutorExample(TaskExecutor taskExecutor, PlaybackDataService playbackDataService) {
	this.taskExecutor = taskExecutor;
	this.playbackDataService = playbackDataService;
    }

    public void fire(final String parameter, final boolean upload) {
	taskExecutor.execute(new Runnable() {
	    public void run() {
		try {
		    log.debug(" Before starting the wait thread ... ");
		    Thread.sleep(10000);
		    log.debug(" After starting the wait thread ... ");
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
		playbackDataService.process(parameter,upload);
	    }
	});
    }
}