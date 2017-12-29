use 4it115;
drop table USERS, CAFE, COFFEE, SPECIAL_OFFER, RATING, POST, OFFERED_COFFEE, OFFERED_SO;
create table USERS (
	USER_ID			integer			not null,
    USER_NAME		varchar(100)	not null,
    USER_SURNAME	varchar(100)	not null,
    EMAIL			varchar(100)	not null,
    ADMIN			boolean			not null,
    constraint PK_USERS primary key (USER_ID)
);
create table CAFE (
	CAFE_ID			integer			not null,
    CAFE_NAME		varchar(100)	not null,
    ACTIVE			boolean			not null,
    constraint PK_CAFE primary key (CAFE_ID)
);
create table COFFEE (
	COFFEE_ID		integer			not null,
    COFFEE_NAME		varchar(100)	not null,
    PRICE			double			not null,
    constraint PK_COFFEE primary key (COFFEE_ID)
);
create table SPECIAL_OFFER (
	OFFER_ID		integer			not null,
    OFFER_NAME		varchar(100)	not null,
    DESCRIPTION		varchar(100)	not null,
    START_DATE		date			not null,
    END_DATE		date			not null,
    constraint PK_SPECIAL_OFFER primary key (OFFER_ID)
);
create table RATING (
	RATING_ID		integer			not null,
    USER_ID			integer,
    CAFE_ID			integer,
    STARS			integer			not null,
    constraint PK_RATING primary key (RATING_ID)
);
create table POST (
	POST_ID			integer			not null,
    USER_ID			integer,
    CAFE_ID			integer,
    POST_TEXT		integer			not null,
    constraint PK_POST primary key (POST_ID)
);

create table OFFERED_COFFEE (
	CAFE_ID			integer			not null,
    COFFEE_ID		integer			not null,
    constraint PK_OFFERED_COFFEE primary key (CAFE_ID,COFFEE_ID)
);
create table OFFERED_SO (
	CAFE_ID			integer			not null,
    SO_ID			integer			not null,
    constraint PK_OFFERED_SO primary key (CAFE_ID,SO_ID)
);

alter table RATING
	add constraint FK_RATING_USER foreign key (USER_ID)
		references USERS(USER_ID)
        on delete cascade;
alter table POST
	add constraint FK_POST_USER foreign key (USER_ID)
		references USERS(USER_ID)
        on delete cascade;
alter table RATING
	add constraint FK_RATING_CAFE foreign key (CAFE_ID)
		references CAFE(CAFE_ID)
        on delete cascade;
alter table POST
	add constraint FK_POST_CAFE foreign key (CAFE_ID)
		references CAFE(CAFE_ID)
        on delete cascade;
alter table OFFERED_COFFEE
	add constraint FK_OFFERED_COFFEE_CAFE foreign key (CAFE_ID)
		references CAFE(CAFE_ID)
        on delete cascade;
alter table OFFERED_COFFEE
	add constraint FK_OFFERED_COFFEE_COFFEE foreign key (COFFEE_ID)
		references COFFEE(COFFEE_ID)
        on delete cascade;
alter table OFFERED_SO
	add constraint FK_OFFERED_SO_CAFE foreign key (CAFE_ID)
		references CAFE(CAFE_ID)
        on delete cascade;
alter table OFFERED_SO
	add constraint FK_OFFERED_SO_SO foreign key (SO_ID)
		references SPECIAL_OFFER(OFFER_ID)
        on delete cascade;