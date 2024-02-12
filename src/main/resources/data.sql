INSERT INTO public.roles
(role_name)
VALUES('ADMIN'),
    ('BANK'),
    ('CLIENT')
on conflict do nothing;

INSERT INTO public.bank
(bank_id, "name")
VALUES(1, 'Альфа Банк'),
       (2, 'Сбер'),
       (3, 'ВТБ'),
       (4, 'РосБанк'),
       (5, 'ПриоБанк'),
       (6, 'СПБ'),
       (7, 'СовКомБанк'),
       (8, 'Русский Стандарт'),
       (9, 'Газпром Банк'),
       (10, 'Московский Банк')
on conflict do nothing;

INSERT INTO public.credit
(max_sum, rate, bank_id, credit_id, date_from, date_to, "name")
VALUES(200000, 10, 1, 1, '2023-01-01', '2023-02-01', 'Семейный'),
      (100000, 11, 2, 2, '2023-01-01', '2023-02-01', 'Универсальный'),
      (100000, 12, 3, 3, '2023-01-01', '2023-02-01', 'Автокредит'),
      (190000, 10.1, 4, 4, '2023-01-01', '2023-02-01', 'Рефинансирование'),
      (1000000, 12.4, 5, 5, '2023-01-01', '2023-02-01', 'Реструктуризация'),
      (3000000, 13.1, 6, 6, '2023-01-01', '2023-02-01', 'Коммерческая ипотека'),
      (100000, 16, 7, 7, '2023-01-01', '2023-02-01', 'Стандарт'),
      (150000, 9.7, 8, 8, '2023-01-01', '2023-02-01', 'Стандарт плюс'),
      (500000, 9.8 , 9, 9, '2023-01-01', '2023-02-01', 'Индивидуальный'),
      (5000000, 10, 10, 10, '2023-01-01', '2023-02-01', 'Коммерческий'),
      (1500000, 15.5, 1, 11, '2023-01-01', '2023-02-01', 'Льготный'),
      (600000, 16, 2, 12, '2023-01-01', '2023-02-01', 'Простой и удобный'),
      (100000, 18, 3, 13, '2023-01-01', '2023-02-01', 'На любые цели'),
      (100000, 14.3, 4, 14, '2023-01-01', '2023-02-01', 'Самый честный'),
      (900000, 12.7, 5, 15, '2023-01-01', '2023-02-01', 'Залоговый+'),
      (800000, 11.8, 6, 16, '2023-01-01', '2023-02-01', 'На все про все'),
      (650000, 7.7, 7, 17, '2023-01-01', '2023-02-01', 'На газификацию'),
      (430000, 16, 8, 18, '2023-01-01', '2023-02-01', 'Газификация домовладений'),
      (450000, 11.7, 9, 19, '2023-01-01', '2023-02-01', 'Стандартный'),
      (10000000, 7, 10, 20, '2023-01-01', '2023-02-01', 'VIP'),
      (1000000, 11, 1, 21, '2023-01-01', '2023-02-01', 'Просто 9,9'),
      (500000, 16.6, 2, 22, '2023-01-01', '2023-02-01', 'Под залог транспорта'),
      (700000, 18, 3, 23, '2023-01-01', '2023-02-01', 'Под залог автомобиля'),
      (890000, 17.4, 4, 24, '2023-01-01', '2023-02-01', 'Акционный (с поручителем)'),
      (2500000, 15, 5, 25, '2023-01-01', '2023-02-01', 'Почетный'),
      (2500000, 12.6, 6, 26, '2023-01-01', '2023-02-01', 'Под залог недвижимости'),
      (340000, 17.3, 7, 27, '2023-01-01', '2023-02-01', 'Твой выбор'),
      (5000000, 13.7, 8, 28, '2023-01-01', '2023-02-01', 'Статусный'),
      (300000, 9.9, 9, 29, '2023-01-01', '2023-02-01', 'Для своих'),
      (400000, 15.04, 10, 30, '2023-01-01', '2023-02-01', 'Оптимистичный'),
      (150000, 19, 1, 31, '2023-01-01', '2023-02-01', 'Без справок'),
      (200000, 5.5, 2, 32, '2023-01-01', '2023-02-01', 'Пенсионный'),
      (700000, 10, 3, 33, '2023-01-01', '2023-02-01', 'Честный'),
      (800000, 19.7, 4, 34, '2023-01-01', '2023-02-01', 'На большие цели'),
      (400000, 12.4, 5, 35, '2023-01-01', '2023-02-01', 'Военный'),
      (7000000, 17.3, 6, 36, '2023-01-01', '2023-02-01', 'Со сниженным платежом'),
      (300000, 11.8, 7, 37, '2023-01-01', '2023-02-01', 'Для пенсионеров'),
      (200000, 12.6, 8, 38, '2023-01-01', '2023-02-01', 'Экспресс'),
      (1000000, 12.9, 9, 39, '2023-01-01', '2023-02-01', 'Образовательный'),
      (200000, 13, 10, 40, '2023-01-01', '2023-02-01', 'Акционный'),
      (5000000, 13.4, 1, 41, '2023-01-01', '2023-02-01', 'Беззалоговый'),
      (2000000, 15.6, 2, 42, '2023-01-01', '2023-02-01', 'На автомобиль'),
      (370000, 15.6, 3, 43, '2023-01-01', '2023-02-01', 'Наличными'),
      (460000, 17.2, 4, 44, '2023-01-01', '2023-02-01', 'Классический'),
      (280000, 18.9, 5, 45, '2023-01-01', '2023-02-01', 'Медицинский'),
      (900000, 19.3, 6, 46, '2023-01-01', '2023-02-01', 'Столичный'),
      (300000, 10, 7, 47, '2023-01-01', '2023-02-01', 'КУБ-Помощь'),
      (550000, 12, 8, 48, '2023-01-01', '2023-02-01', 'Прочный'),
      (1000000, 13, 9, 49, '2023-01-01', '2023-02-01', 'Кредит с поручителем'),
      (500000, 14, 10, 50, '2023-01-01', '2023-02-01', 'Кредит без поручителя'),
      (300000, 16, 1, 51, '2023-01-01', '2023-02-01', 'Молодежный'),
      (100000, 16, 2, 52, '2023-01-01', '2023-02-01', 'Студенческий'),
      (500000, 17, 3, 53, '2023-01-01', '2023-02-01', 'Базовый'),
      (3000000, 18, 4, 54, '2023-01-01', '2023-02-01', 'Дальневосточный'),
      (6000000, 16, 5, 55, '2023-01-01', '2023-02-01', 'Крупный'),
      (1000000, 15, 6, 56, '2023-01-01', '2023-02-01', 'Легкий'),
      (1500000, 14, 7, 57, '2023-01-01', '2023-02-01', 'Сельский'),
      (1500000, 13, 8, 58, '2023-01-01', '2023-02-01', 'Городской'),
      (200000, 12, 9, 59, '2023-01-01', '2023-02-01', 'Спортивный'),
      (1500000, 11, 10, 60, '2023-01-01', '2023-02-01', 'Избранный'),
      (1800000, 11.8, 1, 61, '2023-01-01', '2023-02-01', 'Студенческий'),
      (4300000, 16.9, 2, 62, '2023-01-01', '2023-02-01', 'На развитие бизнеса'),
      (3400000, 13.3, 3, 63, '2023-01-01', '2023-02-01', 'Строительный')
on conflict do nothing;

INSERT INTO public.users
(user_id, last_name, first_name, phone, email)
VALUES(1, 'Олег', 'Иванов', '+79007501112', 'oleg@yandex'),
       (2, 'Ирина', 'Петрова', '+79007501112', 'irina@yandex'),
       (3, 'Александр', 'Рыбкин', '+79007501112', 'alex@yandex'),
       (4, 'Глеб', 'Пушкин', '+79007501112', 'gleb@yandex'),
       (5, 'Ольга', 'Чайка', '+79007501112', 'olfga@yandex')
on conflict do nothing;


INSERT INTO public.bank_user
(user_id, bank_id)
VALUES(1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 2)
on conflict do nothing;



