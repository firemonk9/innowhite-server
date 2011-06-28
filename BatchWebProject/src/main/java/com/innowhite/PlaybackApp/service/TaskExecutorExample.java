package com.innowhite.PlaybackApp.service;

import org.springframework.core.task.TaskExecutor;

public class TaskExecutorExample {

    private TaskExecutor taskExecutor;
    private PlaybackDataService playbackDataService;

    public TaskExecutorExample(TaskExecutor taskExecutor, PlaybackDataService playbackDataService) {
	this.taskExecutor = taskExecutor;
	this.playbackDataService = playbackDataService;
    }

    public void fire(final String parameter) {
	taskExecutor.execute(new Runnable() {
	    public void run() {
		playbackDataService.process(parameter);
	    }
	});
    }
}