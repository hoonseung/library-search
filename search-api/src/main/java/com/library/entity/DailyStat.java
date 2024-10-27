package com.library.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"daily_stat\"")
@Entity
public class DailyStat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "query", nullable = false)
    private String query;
    @Column(name = "eventDateTime", nullable = false)
    private LocalDateTime eventDateTime;


    private DailyStat(String query, LocalDateTime eventDateTime) {
        this.query = query;
        this.eventDateTime = eventDateTime;
    }

    public static DailyStat createEntity(String query, LocalDateTime eventDateTime) {
        return new DailyStat(query, eventDateTime);
    }
}
