
DROP TABLE IF EXISTS "public"."demo";
CREATE TABLE "public"."demo" (
	"id" bigserial,
	"created_by" varchar(50),
	"created_time" timestamp(6) NULL,
	"modified_by" varchar(50),
	"modified_time" timestamp(6) NULL,
	"code" varchar(20) NOT NULL,
	"local_name" varchar(255),
	"age" int4,
	"birthday" timestamp(6) NOT NULL,
	"email" varchar(255)
);

DROP TABLE IF EXISTS "public"."demo_association";
CREATE TABLE "public"."demo_association" (
	"id" bigserial,
	"created_by" varchar(50),
	"created_time" timestamp(6) NULL,
	"modified_by" varchar(50),
	"modified_time" timestamp(6) NULL,
	"demo_id" int8
);

ALTER TABLE "public"."demo" ADD PRIMARY KEY ("id");

ALTER TABLE "public"."demo" ADD CONSTRAINT "demo_age_check" CHECK (((age >= 0) AND (age <= 130)));

ALTER TABLE "public"."demo_association" ADD PRIMARY KEY ("id");

ALTER TABLE "public"."demo_association" ADD CONSTRAINT "fk_lnn35una7f94il6ar0k2gjuhy" FOREIGN KEY ("demo_id") REFERENCES "public"."demo" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;
