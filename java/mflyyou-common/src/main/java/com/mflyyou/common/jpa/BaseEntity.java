package com.mflyyou.common.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    public static final String SOFT_DELETED_CLAUSE = "deleted_at = -1";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "com.mflyyou.jpa.SnowflakeIdentifierGenerator")
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @Column
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Column
    @LastModifiedBy
    private String lastModifiedBy;

    @Column
    @Version
    private Integer version = 0;

    @Column
    private Long deletedAt = -1L;

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || Hibernate.getClass(this)!=Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id!=null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
