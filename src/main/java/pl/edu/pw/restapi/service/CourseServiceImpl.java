package pl.edu.pw.restapi.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.pw.restapi.domain.Course;
import pl.edu.pw.restapi.dto.CourseDTO;
import pl.edu.pw.restapi.dto.mapper.CourseMapper;
import pl.edu.pw.restapi.repository.CourseRepository;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDTO> getCourses(String title, List<Long> categories, List<Long> difficulties, Double priceMin,
                                      Double priceMax, Integer pageNumber, Integer pageSize, Sort.Direction sort) {
        Pageable pageable = getPageable(pageNumber, pageSize, sort);
        List<Course> courses = courseRepository.findAll(title, categories, difficulties, priceMin, priceMax, pageable);
        return CourseMapper.map(courses);
    }

    private Pageable getPageable(Integer pageNumber, Integer pageSize, Sort.Direction sort) {
        pageNumber = pageNumber != null && pageNumber >= 0 ? pageNumber : 0;
        pageSize = pageSize != null && pageSize > 0 ? pageSize : 24;
        sort = sort == null ? Sort.Direction.ASC : sort;

        return PageRequest.of(pageNumber, pageSize, Sort.by(sort, "price"));
    }
}
