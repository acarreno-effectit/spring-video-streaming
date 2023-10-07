package com.acarreno.poc.video.streaming.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acarreno.poc.video.streaming.persistence.entity.ActionEntity;

public interface ActionRepository extends JpaRepository<ActionEntity, String> {

}
