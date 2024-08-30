package com.bit.springboard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeBoardFile is a Querydsl query type for FreeBoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoardFile extends EntityPathBase<FreeBoardFile> {

    private static final long serialVersionUID = 1359814766L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeBoardFile freeBoardFile = new QFreeBoardFile("freeBoardFile");

    public final StringPath filename = createString("filename");

    public final StringPath fileoriginname = createString("fileoriginname");

    public final StringPath filepath = createString("filepath");

    public final StringPath filetype = createString("filetype");

    public final QFreeBoard freeBoard;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QFreeBoardFile(String variable) {
        this(FreeBoardFile.class, forVariable(variable), INITS);
    }

    public QFreeBoardFile(Path<? extends FreeBoardFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeBoardFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeBoardFile(PathMetadata metadata, PathInits inits) {
        this(FreeBoardFile.class, metadata, inits);
    }

    public QFreeBoardFile(Class<? extends FreeBoardFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freeBoard = inits.isInitialized("freeBoard") ? new QFreeBoard(forProperty("freeBoard"), inits.get("freeBoard")) : null;
    }

}

