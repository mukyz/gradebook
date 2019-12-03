SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dimitrije
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `announcements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `announcements` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `announcement` TEXT NOT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `rolename` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(200) NULL DEFAULT NULL,
  `roles_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_users_roles_idx` (`roles_id` ASC),
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`roles_id`)
    REFERENCES `roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `class` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `teacher_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_teacher_id_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_user_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `class_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `class_course` (
  `class_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  INDEX `fk_class_id_idx` (`class_id` ASC),
  INDEX `fk_course_id_idx` (`course_id` ASC),
  CONSTRAINT `fk_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `students` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `date_of_birth` DATE NULL DEFAULT NULL,
  `place_of_birth` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(100) NULL DEFAULT NULL,
  `class_id` INT(11) NULL DEFAULT NULL,
  `parent_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_id_idx` (`class_id` ASC),
  INDEX `fk_parent_user_id_idx` (`parent_id` ASC),
  CONSTRAINT `fk_student_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_parent_id`
    FOREIGN KEY (`parent_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grades` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `student_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `grade` DOUBLE NOT NULL,
  `is_final_grade` TINYINT(4) NOT NULL DEFAULT '0',
  `note` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  INDEX `fk_course_id_idx` (`course_id` ASC),
  CONSTRAINT `fk_grades_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `course` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_grades_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `students` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 123
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `messages` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sender_id` INT(11) NULL DEFAULT NULL,
  `receiver_id` INT(11) NULL DEFAULT NULL,
  `subject` TEXT NOT NULL,
  `message` TEXT NOT NULL,
  `timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_sender_id_idx` (`sender_id` ASC),
  INDEX `fk_receiver_id_idx` (`receiver_id` ASC),
  CONSTRAINT `fk_receiver_id`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `users` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sender_id`
    FOREIGN KEY (`sender_id`)
    REFERENCES `users` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `open_door`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `open_door` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11) NOT NULL,
  `teacher_id` INT(11) NOT NULL,
  `is_accepted` TINYINT(4) NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_parent_id_idx` (`parent_id` ASC),
  INDEX `fk_teacher_Id_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_parent_od_id`
    FOREIGN KEY (`parent_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_od_Id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tests` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `course_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_course_idx` (`course_id` ASC),
  CONSTRAINT `fk_test_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `course` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test_questions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question_text` VARCHAR(200) NOT NULL,
  `test_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_questions_idx` (`test_id` ASC),
  CONSTRAINT `fk_test_questions`
    FOREIGN KEY (`test_id`)
    REFERENCES `tests` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `question_options`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `question_options` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `option_text` TEXT NOT NULL,
  `is_correct` TINYINT(4) NULL DEFAULT '0',
  `value` INT(11) NULL DEFAULT '0',
  `question_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_options_idx` (`question_id` ASC),
  CONSTRAINT `fk_question_options`
    FOREIGN KEY (`question_id`)
    REFERENCES `test_questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `test_resultsr`
-- -----------------------------------------------------
CREATE TABLE `test_results` (
  `student_id` INT NOT NULL,
  `test_id` INT NOT NULL,
  `points_won` INT NULL,
  `points_total` INT NULL,
  PRIMARY KEY (`student_id`, `test_id`),
  INDEX `fk_result_test_idx` (`test_id` ASC),
  CONSTRAINT `fk_result_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `students` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_result_test`
    FOREIGN KEY (`test_id`)
    REFERENCES `tests` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);



-- -----------------------------------------------------
-- Table `student_avatar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_avatar` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `student_id` INT(11) NOT NULL,
  `image` BLOB NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_avatar_idx` (`student_id` ASC),
  CONSTRAINT `fk_student_avatar`
    FOREIGN KEY (`student_id`)
    REFERENCES `students` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `timetable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `course_id` INT(11) NOT NULL,
  `class_id` INT(11) NOT NULL,
  `day` INT(11) NOT NULL,
  `order_num` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_course_timetable_id_idx` (`course_id` ASC),
  INDEX `fk_class_timetable_id_idx` (`class_id` ASC),
  CONSTRAINT `fk_class_timetable_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_timetable_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
