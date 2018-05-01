package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContentService {

    /** The content repository */
    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public Map<String, ?> listAll(int page) {
        return listAllImpl(null, null, null, page);
    }

    public Map<String, ?> listAll(int year, int page) {
        return listAllImpl(year, null, null, page);
    }

    public Map<String, ?> listAll(int year, int month, int page) {
        return listAllImpl(year, month, null, page);
    }

    public Map<String, ?> listAll(int year, int month, int day, int page) {
        return listAllImpl(year, month, day, page);
    }

    private Map<String, ?> listAllImpl(Integer year, Integer month, Integer day, int page) {
        // Calculate the start/end publication date
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (year != null && month != null && day != null) {
            LocalDate date = LocalDate.of(year, month, day);
            startDateTime = LocalDateTime.of(date, LocalTime.MIN);
            endDateTime = LocalDateTime.of(date, LocalTime.MAX);
        } else if (year != null && month != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        } else if (year != null) {
            LocalDate startDate = LocalDate.of(year, 1, 1);
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            endDateTime = LocalDateTime.of(endDate, LocalTime.MIN);
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = null;
        if (startDateTime == null && endDateTime == null) {
            contents = contentRepository.findAll(pageRequest);
        } else {
            contents = contentRepository.findByPublishedAtBetween(startDateTime, endDateTime, pageRequest);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("contents", contents);
        model.put("archives", contentRepository.countByMonth());

        return model;
    }
}
