-- 사용자
ALTER TABLE member
	DROP CONSTRAINT IF EXISTS PK_member; -- 사용자 기본키

-- 토큰
ALTER TABLE refresh_token
	DROP CONSTRAINT IF EXISTS PK_refresh_token; -- 토큰 기본키

-- 사용자
DROP TABLE IF EXISTS member;

-- 토큰
DROP TABLE IF EXISTS refresh_token;

-- 사용자_순번
DROP SEQUENCE IF EXISTS sq_member_seq;

-- 사용자_순번
CREATE SEQUENCE sq_member_seq
	INCREMENT 1
	MAXVALUE 999999999999999
	START 1;

-- 사용자_순번
COMMENT ON SEQUENCE sq_member_seq IS '사용자_순번';

-- 사용자
CREATE TABLE member
(
	seq    NUMERIC(15)  NOT NULL, -- 순번
	id     VARCHAR(30)  NOT NULL, -- ID
	pwd    VARCHAR(256) NOT NULL, -- 비밀번호
	auth   VARCHAR(30)  NULL,     -- 인증
	reg_dt TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP, -- 등록_일시
	chg_dt TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP -- 수정_일시
);

-- 사용자
COMMENT ON TABLE member IS '사용자';

-- 순번
COMMENT ON COLUMN member.seq IS '순번';

-- ID
COMMENT ON COLUMN member.id IS 'ID';

-- 비밀번호
COMMENT ON COLUMN member.pwd IS '비밀번호';

-- 인증
COMMENT ON COLUMN member.auth IS '인증';

-- 등록_일시
COMMENT ON COLUMN member.reg_dt IS '등록_일시';

-- 수정_일시
COMMENT ON COLUMN member.chg_dt IS '수정_일시';

-- 사용자 기본키
CREATE UNIQUE INDEX PK_member
	ON member
	( -- 사용자
		seq ASC -- 순번
	)
;
-- 사용자
ALTER TABLE member
	ADD CONSTRAINT PK_member
		 -- 사용자 기본키
	PRIMARY KEY
	USING INDEX PK_member;

-- 사용자 기본키
COMMENT ON CONSTRAINT PK_member ON member IS '사용자 기본키';

-- 토큰
CREATE TABLE refresh_token
(
	key   VARCHAR(30)  NOT NULL, -- 키
	value VARCHAR(256) NOT NULL  -- 값
);

-- 토큰
COMMENT ON TABLE refresh_token IS '토큰';

-- 키
COMMENT ON COLUMN refresh_token.key IS '키';

-- 값
COMMENT ON COLUMN refresh_token.value IS '값';

-- 토큰 기본키
CREATE UNIQUE INDEX PK_refresh_token
	ON refresh_token
	( -- 토큰
		key ASC -- 키
	)
;
-- 토큰
ALTER TABLE refresh_token
	ADD CONSTRAINT PK_refresh_token
		 -- 토큰 기본키
	PRIMARY KEY
	USING INDEX PK_refresh_token;

-- 토큰 기본키
COMMENT ON CONSTRAINT PK_refresh_token ON refresh_token IS '토큰 기본키';