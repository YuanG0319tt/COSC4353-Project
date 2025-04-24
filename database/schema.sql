CREATE TABLE `event_details` (
                                 `eventid` int NOT NULL AUTO_INCREMENT,
                                 `description` varchar(255) DEFAULT NULL,
                                 `event_date` date DEFAULT NULL,
                                 `event_name` varchar(255) DEFAULT NULL,
                                 `location` varchar(255) DEFAULT NULL,
                                 `required_skills` varchar(255) DEFAULT NULL,
                                 `urgency` int DEFAULT NULL,
                                 PRIMARY KEY (`eventid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `EventDetails` (
                                `EventID` int NOT NULL AUTO_INCREMENT,
                                `EventName` varchar(255) NOT NULL,
                                `Description` text,
                                `Location` varchar(255) DEFAULT NULL,
                                `RequiredSkills` text,
                                `Urgency` int DEFAULT NULL,
                                `EventDate` date NOT NULL,
                                PRIMARY KEY (`EventID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notice`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `users`  (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `user_info` (
                             `userid` bigint NOT NULL,
                             `address1` varchar(255) DEFAULT NULL,
                             `address2` varchar(255) DEFAULT NULL,
                             `availability` varchar(255) DEFAULT NULL,
                             `city` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `full_name` varchar(255) DEFAULT NULL,
                             `phone_number` varchar(255) DEFAULT NULL,
                             `preferences` varchar(255) DEFAULT NULL,
                             `skills` varchar(255) DEFAULT NULL,
                             `state` varchar(255) DEFAULT NULL,
                             `zip_code` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`userid`),
                             CONSTRAINT `FK9ewj7stvmjirwa22vvddbs3sk` FOREIGN KEY (`userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `UserCredentials` (
                                   `UserID` int NOT NULL AUTO_INCREMENT,
                                   `Password` varchar(255) NOT NULL,
                                   `Username` varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `UserInfo` (
                            `UserID` int NOT NULL,
                            `FullName` varchar(255) NOT NULL,
                            `City` varchar(100) DEFAULT NULL,
                            `State` varchar(100) DEFAULT NULL,
                            `ZipCode` varchar(20) DEFAULT NULL,
                            `Skills` text,
                            `Preferences` text,
                            `Availability` text,
                            `Email` varchar(255) DEFAULT NULL,
                            `PhoneNumber` varchar(20) DEFAULT NULL,
                            `Address1` varchar(255) DEFAULT NULL,
                            `Address2` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`UserID`),
                            CONSTRAINT `UserInfo_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `UserCredentials` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `userprofile`;
CREATE TABLE `userprofile`  (
                                `uid` int(11) NOT NULL COMMENT 'User ID',
                                `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Full Name',
                                `address1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Address Line 1',
                                `address2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Address Line 2',
                                `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'City',
                                `state` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'State',
                                `zip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Zip Code',
                                `skills` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Skills',
                                `preferences` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Preferences',
                                `availability` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Availability',
                                PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `volunteer_history` (
                                    `HistoryID` bigint NOT NULL AUTO_INCREMENT,
                                    `UserID` int DEFAULT NULL,
                                    `EventID` int DEFAULT NULL,
                                    `ParticipationDate` date DEFAULT NULL,
                                    `HoursVolunteered` int DEFAULT '0',
                                    `email` varchar(255) DEFAULT NULL,
                                    `event_date` varchar(255) DEFAULT NULL,
                                    `event_name` varchar(255) DEFAULT NULL,
                                    `hours_volunteered` int DEFAULT NULL,
                                    `name` varchar(255) DEFAULT NULL,
                                    `phone_number` varchar(255) DEFAULT NULL,
                                    `status` varchar(255) DEFAULT NULL,
                                    PRIMARY KEY (`HistoryID`),
                                    KEY `UserID` (`UserID`),
                                    KEY `EventID` (`EventID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;