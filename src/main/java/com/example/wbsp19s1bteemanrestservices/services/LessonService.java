package com.example.wbsp19s1bteemanrestservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.wbsp19s1bteemanrestservices.model.Module;
import com.example.wbsp19s1bteemanrestservices.services.ModuleService;
import com.example.wbsp19s1bteemanrestservices.model.Lesson;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class LessonService {

	private Lesson one = new Lesson(123, "Lesson 1");
	private Lesson two = new Lesson(234, "Lesson 2");
	public static List<Lesson> lessons = new ArrayList<Lesson>();
	{
		lessons.add(one);
		lessons.add(two);
	}
	
	@PostMapping("/api/modules/{mid}/lessons")
	public List<Lesson> createLesson(
			@PathVariable("mid") Integer mid,
			@RequestBody Lesson lesson) {
		lesson.setId(((Double)(Math.random() * 10000000)).intValue());
		for(Module mod : ModuleService.modules) {
			if(mod.getId().intValue() == mid) {
				mod.addLesson(lesson);
				lessons.add(lesson);
				return mod.getLessons();
			}
		}
		return null;
	}
	
	@GetMapping("/api/modules/{mid}/lessons")
	public List<Lesson> findAllLessons(
			@PathVariable("mid") Integer mid) {
		for(Module mod : ModuleService.modules) {
			if(mod.getId().intValue() == mid) {
				return mod.getLessons();
			}
		}
		return null;
	}
	
	@GetMapping("/api/lessons/{lid}")
	public Lesson findLessonById(
			@PathVariable("lid") Integer lid) {
		for(Lesson les : lessons) {
			if(les.getId().intValue() == lid) {
				return les;
			}
		}
		return null;
	}
	
	@PutMapping("/api/lessons/{lid}")
	public Lesson updateLesson(
			@PathVariable("lid") Integer lid,
			@RequestBody Lesson lesson) {
		for(Lesson les : lessons) {
			if(les.getId().intValue() == lid) {
				les.setTitle(lesson.getTitle());
				les.setTopics(lesson.getTopics());
				return les;
			}
		}
		return null;
	}
	
	@DeleteMapping("/api/lessons/{lid}")
	public List<Lesson> deleteLesson(
			@PathVariable("lid") Integer lid) {
		lessons = lessons.stream()
				.filter(lesson -> lesson.getId().intValue() != lid)
			    .collect(Collectors.toList());
		return lessons;
	}
}
