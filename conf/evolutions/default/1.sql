# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                        bigint not null,
  title                     varchar(255) not null,
  category                  varchar(255) not null,
  content                   TEXT,
  user_id                   bigint,
  constraint pk_article primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255) not null,
  sha_password              varbinary(64) not null,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

create sequence article_seq;

create sequence user_seq;

alter table article add constraint fk_article_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_article_user_1 on article (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists article;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists article_seq;

drop sequence if exists user_seq;

