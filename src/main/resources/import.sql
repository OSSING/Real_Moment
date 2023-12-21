-- Users Table 더미데이터
INSERT INTO users (user_id, grade_id, id, password, email, name, tel, birth_date, gender, point, recently_login, statue, deleted, login_status)
VALUES
(1, 1, 'user1', 'password1', 'user1@example.com', 'User 1', '010-2456-7890', '1998-01-01', 'M', 100, '2023-11-01 12:00:00', TRUE, FALSE, FALSE),
(2, 2, 'user2', 'password2', 'user2@example.com', 'User 2', '010-1654-3210', '1995-03-15', 'F', 50, '2023-10-02 09:30:00', TRUE, FALSE, TRUE),
(3, 1, 'user3', 'password3', 'user3@example.com', 'User 3', '010-5222-3333', '1988-07-20', 'M', 200, '2023-01-03 15:45:00', TRUE, FALSE, FALSE);

-- Grades Table 더미데이터
INSERT INTO grades (grade_id, grade, reward_rate) VALUES
(1, 'WHITE', 1),
(2, 'SILVER', 2),
(3, 'GOLD', 3);

-- Addresses Table 더미데이터
INSERT INTO addresses (address_id, user_id, name, address, det_address, is_def_address)
VALUES
(1, 1, 'address1', '대전 유성구', '101동 503호', TRUE),
(2, 2, 'address2', '대전 서구', '503동 203호', FALSE),
(3, 3, 'address3', '대전 중구', '509호', FALSE);

-- Admins Table 더미데이터
INSERT INTO admins (admin_id, id, password, email, name, grade) VALUES
(1, 'admin1', 'password1', 'admin1@example.com', 'Admin 1', 1),
(2, 'admin2', 'password2', 'admin2@example.com', 'Admin 2', 2),
(4, 'admin4', 'password4', 'admin4@example.com', 'Admin 4', 4);
(5, 'admin5', 'password5', 'admin5@example.com', 'Admin 5', 5);

-- Announcements Table 더미데이터
INSERT INTO announcements (notice_id, admin_id, title, content, fix, view_count, created_date, modified_date)
VALUES
(1, 1, 'Announcement title1', 'Announcement content1', TRUE, 100, '2023-01-05 10:00:00', '2023-01-05 11:30:00'),
(2, 2, 'Announcement title2', 'Announcement content2', FALSE, 50, '2023-01-06 09:30:00', '2023-01-06 10:45:00'),
(3, 3, 'Announcement title3', 'Announcement content3', TRUE, 75, '2023-01-07 14:15:00', '2023-01-07 15:20:00');

-- Carts Table 더미데이터
INSERT INTO carts (cart_id, user_id, item_id, stock, price, checked) VALUES
(1, 1, 101, 2, 30, TRUE),
(2, 2, 102, 1, 50, FALSE),
(3, 3, 103, 3, 20, TRUE);

-- Category Table 더미데이터
INSERT INTO category (category_id, category, parent_id) VALUES
(1, 'parent category1', NULL),
(2, 'parent category2', NULL),
(3, 'child1', 1),
(4, 'child2', 1),
(5, 'child3', 2),
(6, 'child4', 2);

-- Items Table 더미데이터
INSERT INTO items (item_id, category_id, name, content, price, discount_rate, sell_price, stock, sell_check, delete_check, created_date, modified_date, main_img, serve_img)
VALUES
(101, 3, 'item1', 'item content1', 800, 10, 720, 50, TRUE, FALSE, '2023-08-29 10:00:00', '2023-09-02 11:30:00', 'main1.jpg', 'serve1.jpg'),
(102, 4, 'item2', 'item content2', 1200, 5, 1140, 30, TRUE, FALSE, '2023-05-02 09:30:00', '2023-05-06 10:45:00', 'main2.jpg', 'serve2.jpg'),
(103, 5, 'item3', 'item content3', 1000, 0, 1000, 100, TRUE, FALSE, '2023-01-02 09:30:00', '2023-01-07 10:45:00', 'main3.jpg', 'serve3.jpg');

-- Orders Table 더미데이터
INSERT INTO orders (order_id, user_id, ordered_date, sipped_date, price, name, address, det_address, request, tel, status, refund_text)
VALUES
(1, 1, '2023-01-01 12:30:00', NULL, 1440, '김도은', '대전 유성구', '101동 503호', '깨질 수 있으니 조심해주세요', '010-1555-1234', '배송중', NULL),
(2, 2, '2023-01-02 10:00:00', '2023-01-04 12:30:00', 1140, '길오성', '대전 서구', '503동 203호', '안전하게 배송해주세요', '010-3555-5678', '배송완료', NULL),
(3, 3, '2023-01-03 16:00:00', NULL, 3000, '안휘진', '대전 중구', '509호', NULL, '010-2555-9876', '배송 전 연락바랍니다', '환불 사유');

-- OrderDetails Table 더미데이터
INSERT INTO order_details (order_det_id, item_id, order_id, fixed_price, discount_rate, sell_price, item_count)
VALUES
(1, 101, 1, 8000, 10, 720, 2),
(2, 102, 2, 1200, 5, 1140, 1),
(3, 103, 3, 1000, 0, 1000, 3);

-- QA Table 더미데이터
INSERT INTO qa (qa_id, item_id, user_id, title, content, written_date, edited_date)
VALUES
(1, 101, 1, 'QA title1', 'QA content1', '2023-03-05 10:30:00', '2023-03-07 11:00:00'),
(2, 102, 2, 'QA title2', 'QA content2', '2023-07-06 11:00:00', NULL),
(3, 103, 3, 'QA title3', 'QA content3', '2023-09-07 15:00:00', '2023-09-15 15:30:00');

-- QAComments Table 더미데이터
INSERT INTO qa_comments (qa_comment_id, admin_id, qa_id, content, written_date, edited_date)
VALUES
(1, 1, 1, 'QA_comments content1', '2023-03-07 12:00:00', '2023-03-07 12:45:00'),
(2, 2, 2, 'QA_comments content2', '2023-07-07 14:30:00', NULL),
(3, 3, 3, 'QA_comments content3', '2023-09-08 11:45:00', '2023-09-08 12:30:00');

-- Reviews Table 더미데이터
INSERT INTO reviews (review_id, user_id, item_id, title, content, star, written_date, edited_date)
VALUES
(1, 1, 101, 'review title1', 'review content1', 5, '2023-01-05 11:30:00', '2023-01-05 12:00:00'),
(2, 2, 102, 'review title1', 'review content2', 4, '2023-01-06 10:45:00', NULL),
(3, 3, 103, 'review title1', 'review content3', 5, '2023-01-07 15:20:00', '2023-01-07 15:50:00');

-- Wishlist Table 더미데이터
INSERT INTO wishlist (wishlist_id, user_id, item_id)
VALUES
(1, 1, 102),
(2, 2, 103),
(3, 3, 101);