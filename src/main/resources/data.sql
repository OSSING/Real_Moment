-- Level Table 더미데이터
INSERT INTO level (level, reward_rate) VALUES
('WHITE', 1),
('SILVER', 2),
('GOLD', 3);

-- Member Table 더미데이터
INSERT INTO member (level_id, id, email, password, name, tel, birth_date, gender, point, recently_login, is_member_status, member_role, activated) VALUES
(1, 'user1', 'user1@example.com', 'password1', 'User 1', '010-2456-7890', '19980101', 'M', 100, '2023-11-01 12:00:00', TRUE, 'ROLE_MEMBER', TRUE),
(2, 'user2', 'user2@example.com', 'password2', 'User 2', '010-1654-3210', '19950315', 'F', 50, '2023-10-02 09:30:00', TRUE, 'ROLE_MEMBER', TRUE),
(1, 'user3', 'user3@example.com', 'password3', 'User 3', '010-5222-3333', '19880720', 'M', 200, '2023-01-03 15:45:00', TRUE, 'ROLE_MEMBER', TRUE);

-- Addresses Table 더미데이터
INSERT INTO addresses (member_id, name, address, det_address, is_def_address) VALUES
(1, 'address1', '대전 유성구', '101동 502호', TRUE),
(1, 'address2', '대전 유성구', '101동 503호', FALSE),
(2, 'address3', '대전 서구', '503동 203호', TRUE),
(2, 'address4', '대전 서구', '503동 204호', FALSE),
(3, 'address5', '대전 중구', '508호', TRUE),
(3, 'address6', '대전 중구', '509호', FALSE);

-- Authority Table 더미데이터
INSERT INTO authority (authority_name) VALUES
('ROLE_ADMIN1'),
('ROLE_ADMIN2'),
('ROLE_ADMIN3');

-- Admin Table 더미데이터
INSERT INTO admin (id, password, email, name, activated) VALUES
('adminId1', 'password1', 'admin1@example.com', 'adminName1', TRUE),
('adminId2', 'password2', 'admin2@example.com', 'adminName2', TRUE),
('adminId3', 'password3', 'admin2@example.com', 'adminName3', TRUE),
('adminId4', 'password4', 'admin4@example.com', 'adminName4', TRUE),
('adminId5', 'password5', 'admin5@example.com', 'adminName5', TRUE);

-- admin_authority Table 더미데이터 (Authority와 Admin 테이블의 중간 테이블)
INSERT INTO admin_authority (admin_id, authority_name) VALUES
(1, 'ROLE_ADMIN1'),
(1, 'ROLE_ADMIN2'),
(1, 'ROLE_ADMIN3'),
(2, 'ROLE_ADMIN2'),
(2, 'ROLE_ADMIN3'),
(3, 'ROLE_ADMIN3'),
(4, 'ROLE_ADMIN3'),
(5, 'ROLE_ADMIN3');

-- Announcements Table 더미데이터
INSERT INTO announcement (admin_id, title, content, is_fix, view_count)
VALUES
(1, 'Announcement title1', 'Announcement content1', TRUE, 100),
(2, 'Announcement title2', 'Announcement content2', FALSE, 50),
(4, 'Announcement title3', 'Announcement content3', TRUE, 75);

-- Category Table 더미데이터
INSERT INTO category (category_name, parent_category) VALUES
('parent category1', NULL),
('parent category2', NULL),
('child1', 1),
('child2', 1),
('child3', 2),
('child4', 2);

-- Items Table 더미데이터
INSERT INTO item (category_id, name, content, price, discount_rate, sell_price, stock, is_sell_check, is_delete_check, created_date, modified_date, main_img, serve_img)
VALUES
(3, 'item1', 'item content1', 800, 10, 720, 50, TRUE, FALSE, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 'main1.jpg', 'serve1.jpg'),
(4, 'item2', 'item content2', 1200, 5, 1140, 30, TRUE, FALSE, '2023-05-02 09:30:00', '2023-05-06 10:45:00', 'main2.jpg', 'serve2.jpg'),
(5, 'item3', 'item content3', 1000, 0, 1000, 100, TRUE, FALSE, '2023-01-02 09:30:00', '2023-01-07 10:45:00', 'main3.jpg', 'serve3.jpg');

-- Carts Table 더미데이터
INSERT INTO cart (member_id, item_id, stock, price, checked) VALUES
(1, 1, 2, 30, TRUE),
(2, 2, 1, 50, FALSE),
(3, 3, 3, 20, TRUE);

-- Orders Table 더미데이터
INSERT INTO orders (member_id, ordered_date, shipped_date, price, name, address, det_address, request, tel, status, refund_text)
VALUES
(1, '2023-01-01 12:30:00', NULL, 1440, '김도은', '대전 유성구', '101동 503호', '깨질 수 있으니 조심해주세요', '010-1555-1234', '배송중', NULL),
(2, '2023-01-02 10:00:00', '2023-01-04 12:30:00', 1140, '길오성', '대전 서구', '503동 203호', '안전하게 배송해주세요', '010-3555-5678', '배송완료', NULL),
(3, '2023-01-03 16:00:00', NULL, 3000, '안휘진', '대전 중구', '509호', NULL, '010-2555-9876', '배송 전 연락바랍니다', '환불 사유');

-- OrderDetails Table 더미데이터
INSERT INTO order_detail (item_id, order_id, fixed_price, discount_rate, sell_price, count)
VALUES
(1, 1, 8000, 10, 720, 2),
(2, 2, 1200, 5, 1140, 1),
(3, 3, 1000, 0, 1000, 3);

-- QA Table 더미데이터
INSERT INTO item_qa (item_id, member_id, title, content)
VALUES
(1, 1, 'QA title1', 'QA content1'),
(2, 2, 'QA title2', 'QA content2'),
(3, 3, 'QA title3', 'QA content3');

-- QAComments Table 더미데이터
INSERT INTO qa_comment (admin_id, item_qa_id, content)
VALUES
(1, 1, 'QA_comments content1'),
(2, 2, 'QA_comments content2'),
(3, 3, 'QA_comments content3');

-- Reviews Table 더미데이터
INSERT INTO review (member_id, item_id, title, content, star)
VALUES
(1, 1, 'review title1', 'review content1', 5),
(2, 2, 'review title1', 'review content2', 4),
(3, 3, 'review title1', 'review content3', 5);

-- Wishlist Table 더미데이터
INSERT INTO wishlist (member_id, item_id)
VALUES
(1, 2),
(2, 3),
(3, 1);