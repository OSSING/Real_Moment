-- Level Table 더미데이터
INSERT INTO grade (grade_name, reward_rate, grade_price) VALUES
('Moment', 1, 0),
('Beginning', 2, 50000),
('Finally', 3, 100000),
('Forever', 4, 200000);

-- Member Table 더미데이터
INSERT INTO member (grade_id, login_id, login_password, email,  name, tel, birth_date, gender, point, this_year_pay, recently_login, is_delete, roles) VALUES
(1, 'user1', '$2a$10$6tJIAdSoVbunKE9yZKSiGOkn7zdPux8sTy/c5/1/R9FJoZ3pLT8Sm', 'user1@example.com', 'User 1', '010-2456-7890', '19980101', 'MAN', 100, 53000, '2023-11-01 12:00:00', false, 'ROLE_MEMBER'),
(2, 'user2', '$2a$10$4Ix3u/.AQTaP3ZgPOvBzDuWF5mFuopY6WZRx0avQh8n13D57hlEP6', 'user2@example.com', 'User 2', '010-1654-3210', '19950315', 'WOMAN', 50, 15000, '2023-10-02 09:30:00', false, 'ROLE_MEMBER'),
(4, 'user3', '$2a$10$whdwtf4XIKAtzXYBCgwEEeTcvP/gfOZ7LtoweLUL2Ld.voqmPCRUa', 'user3@example.com', 'User 3', '010-5222-3333', '19880720', 'MAN', 200, 200000, '2023-01-03 15:45:00', false, 'ROLE_MEMBER');

-- Addresses Table 더미데이터
INSERT INTO address (member_id, name, tel, main_address, det_address, is_def_address) VALUES
(1, 'address1', '010-3187-4011', '대전 유성구', '101동 502호', TRUE),
(1, 'address2', '010-1234-5678', '대전 유성구', '101동 503호', FALSE),
(2, 'address3', '010-4321-7654', '대전 서구', '503동 203호', TRUE),
(2, 'address4', '010-3187-4022', '대전 서구', '503동 204호', FALSE),
(3, 'address5', '010-3187-5352', '대전 중구', '508호', TRUE),
(3, 'address6', '010-1287-4871', '대전 중구', '509호', FALSE);

-- Admin Table 더미데이터
INSERT INTO admin (login_id, login_password, email, name, is_delete, roles) VALUES
('adminId1', '$2a$10$3rNtn9JeSQmg3MTk1uBxS.Y1Ks.qdG2WKmiOxWSqbHVh0aYkJyg22', 'admin1@example.com', 'adminName1', false, 'ROLE_CUSTOMER'),
('adminId2', '$2a$10$1mVZ/tnAvI9NqFZEULjf2OJt7BorY1vXlukgyPxJ83DZ0ocRpY5sC', 'admin2@example.com', 'adminName2', false, 'ROLE_CUSTOMER'),
('adminId3', '$2a$10$qkiojc.QSXcGxOOPKDo0Ruu2TlPR5d3VhGkOOQf80BLBIWadvgTbO', 'admin2@example.com', 'adminName3', false, 'ROLE_OPERATOR'),
('adminId4', '$2a$10$HK7tpwoV4THD9Hb1Fs3bGunn13UudbdM7DtploEHBa72Qg3RCOIV.', 'admin4@example.com', 'adminName4', false, 'ROLE_OPERATOR'),
('adminId5', '$2a$10$tXCITPXWabNySF2yF149XOME4QpcL87jCKlvgsGBHf7PbQQvE9iNy', 'admin5@example.com', 'adminName5', false, 'ROLE_REPRESENTATIVE');

-- Announcement Table 더미데이터
INSERT INTO announcement (admin_id, title, content, is_fix, view_count)
VALUES
(1, 'Announcement title1', 'Announcement content1', TRUE, 100),
(2, 'Announcement title2', 'Announcement content2', FALSE, 50),
(4, 'Announcement title3', 'Announcement content3', TRUE, 75);

-- Category Table 더미데이터
INSERT INTO category (name, parent_category) VALUES
('parent category1', NULL),
('parent category2', NULL),
('child1', 1),
('child2', 1),
('child3', 2),
('child4', 2);

-- Items Table 더미데이터
INSERT INTO item (category_id, name, content, price, discount_rate, discount_price, sell_price, stock, sell_count, is_sell, is_delete)
VALUES
(3, 'item1', 'item content1', 800, 10, 80, 720, 50, 30, TRUE, FALSE),
(4, 'item2', 'item content2', 1200, 5, 60, 1140, 30, 50, TRUE, FALSE),
(5, 'item3', 'item content3', 1000, 0, 0, 1000, 100, 70, TRUE, FALSE);

-- Carts Table 더미데이터
INSERT INTO cart (member_id, item_id, stock, price) VALUES
(1, 1, 2, 30),
(2, 2, 1, 50),
(3, 3, 3, 20);

-- Orders Table 더미데이터
INSERT INTO orders (member_id, ordered_date, delivery_date, total_price, total_discount_price, use_point, get_point, buy_price, name, main_address, det_address, request_text, tel, status, reason_text, merchant_uid, imp_uid)
VALUES
(1, '2023-01-01 12:30:00', NULL, 16000, 1600, 0, 144, 14400,  '김도은', '대전 유성구', '101동 503호', '깨질 수 있으니 조심해주세요', '010-1555-1234', 'DELIVERY_DOING', NULL, 123, 123),
(2, '2023-01-02 10:00:00', '2023-01-04 12:30:00', 1200, 60, 0, 23, 1140, '길오성', '대전 서구', '503동 203호', '안전하게 배송해주세요', '010-3555-5678', 'DELIVERY_DONE', NULL, 456, 456),
(3, '2023-01-03 16:00:00', NULL, 3000, 0, 0, 120, 3000, '안휘진', '대전 중구', '509호', '배송 전 연락바랍니다', '010-2555-9876', 'PAYMENT_DONE', NULL, 789, 789);

-- OrderDetails Table 더미데이터
INSERT INTO order_detail (item_id, order_id, price, discount_rate, discount_price, sell_price, item_count, total_price)
VALUES
(1, 1, 8000, 10, 800, 7200, 2, 14400),
(2, 2, 1200, 5, 60, 1140, 1, 1140),
(3, 3, 1000, 0, 0, 1000, 3, 3000);

-- QA Table 더미데이터
INSERT INTO item_qa (item_id, member_id, title, content, is_answer)
VALUES
(1, 1, 'QA title1', 'QA content1', false),
(2, 2, 'QA title2', 'QA content2', true),
(3, 3, 'QA title3', 'QA content3', true);

-- QAComments Table 더미데이터
INSERT INTO qa_comment (admin_id, item_qa_id, content)
VALUES
(1, 1, 'QA_comments content1'),
(2, 2, 'QA_comments content2'),
(3, 2, 'QA_comments content3');

-- Reviews Table 더미데이터
INSERT INTO review (member_id, item_id, title, content, star)
VALUES
(1, 1, 'review title1', 'review content1', 5),
(2, 2, 'review title2', 'review content2', 4),
(3, 3, 'review title3', 'review content3', 5);

-- Wishlist Table 더미데이터
INSERT INTO wish (member_id, item_id)
VALUES
(1, 2),
(2, 3),
(3, 1);

-- OneOnOne Table 더미데이터
INSERT INTO one_on_one (member_id, title, content, is_answer)
VALUES
(1, 'OneOnOne title1', 'OneOnOne content1', true),
(2, 'OneOnOne title2', 'OneOnOne content2', true),
(3, 'OneOnOne title3', 'OneOnOne content3', false);

-- Comment Table 더미데이터
INSERT INTO comment (admin_id, one_on_one_id, content)
VALUES
(1, 1, 'OneOnOne content1'),
(1, 2, 'OneOnOne content2');

-- S3File Table 더미데이터
INSERT INTO s3_file (file_name, file_url)
VALUES
('fileName1', 'fileUrl1'),
('fileName2', 'fileUrl2'),
('fileName3', 'fileUrl3'),
('fileName4', 'fileUrl4'),
('fileName5', 'fileUrl5'),
('fileName6', 'fileUrl6'),
('fileName7', 'fileUrl7'),
('fileName8', 'fileUrl8'),
('fileName9', 'fileUrl9'),
('fileName10', 'fileUrl10'),
('fileName11', 'fileUrl11'),
('fileName12', 'fileUrl12'),
('fileName13', 'fileUrl13'),
('fileName14', 'fileUrl14'),
('fileName15', 'fileUrl15');

-- ItemFile Table 더미데이터
INSERT INTO item_file (s3_file_id, item_id, main_or_sub, number)
VALUES
(1, 1, 'main', 0),
(2, 1, 'main', 1),
(3, 1, 'main', 2),
(4, 1, 'sub', 0),
(5, 1, 'sub', 1),
(6, 1, 'sub', 2),
(7, 2, 'main', 0),
(8, 2, 'main', 1),
(9, 2, 'main', 2),
(10, 2, 'sub', 0),
(11, 2, 'sub', 1),
(12, 3, 'main', 0),
(13, 3, 'main', 1),
(14, 3, 'sub', 0),
(15, 3, 'sub', 1);