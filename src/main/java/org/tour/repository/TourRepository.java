package org.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tour.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {

	@Query("Select t from Tour t where lower(t.name) like %:name%")
	public List<Tour> findToursByNameLike(@Param("name") String name);
}
