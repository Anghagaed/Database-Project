/* AccountInfo */
INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (1, 1, "A", "test1@gmail.com", "CA", "A Ave", 1, "01-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (2, 2, "B", "test2@gmail.com", "CA", "B Ave", 1, "02-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (3, 3, "C", "test3@gmail.com", "CA", "C Ave", 1, "03-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (4, 4, "D", "test4@gmail.com", "CA", "D Ave", 1, "04-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (5, 5, "E", "test5@gmail.com", "CA", "E Ave", 1, "05-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (6, 6, "F", "test6@gmail.com", "CA", "F Ave", 1, "06-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (7, 7, "G", "test7@gmail.com", "CA", "G Ave", 1, "07-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (8, 8, "H", "test8@gmail.com", "CA", "H Ave", 1, "08-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (9, 9, "I", "test9@gmail.com", "CA", "I Ave", 1, "09-11-2018");

INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate)
VALUES (10, 10, "J", "test10@gmail.com", "CA", "J Ave", 1, "10-11-2018");

/* EncryptedWallet */
INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (1, 1);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (2, 2);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (4, 4);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (3, 3);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (5, 5);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (6, 6);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (7, 7);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (8, 8);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (9, 9);

INSERT INTO encryptedwallet (ew_encryptid, ew_id) 
VALUES (10, 10);

/* encryptednote */
INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (1, 1);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (2, 2);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (4, 4);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (3, 3);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (5, 5);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (6, 6);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (7, 7);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (8, 8);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (9, 9);

INSERT INTO encryptedwallet (en_encryptid, en_id) 
VALUES (10, 10);

/* encryptedpasswordentry */

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (1, 1);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (2, 2);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (4, 4);

INSERT INTO encryptedwallet (epe_encryptid, epe_id)  
VALUES (3, 3);

INSERT INTO encryptedwallet (epe_encryptid, epe_id)  
VALUES (5, 5);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (6, 6);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (7, 7);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (8, 8);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (9, 9);

INSERT INTO encryptedwallet (epe_encryptid, epe_id) 
VALUES (10, 10);

/* passwordentry */

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (1, "www.google.com", 1, "test1@gmail", "password", 1, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (2, "www.google.com", 2, "test2@gmail", "password", 2, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (3, "www.google.com", 3, "test3@gmail", "password", 3, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (4, "www.google.com", 4, "test4@gmail", "password", 4, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (5, "www.google.com", 5, "test5@gmail", "password", 5, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (6, "www.google.com", 6, "test6@gmail", "password", 6, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (7, "www.google.com", 7, "test7@gmail", "password", 7, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (8, "www.google.com", 8, "test8@gmail", "password", 8, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (9, "www.google.com", 9, "test9@gmail", "password", 9, "1234567890");

INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptid, p_cipher) 
VALUES (10, "www.google.com", 10, "test10@gmail", "password", 10, "1234567890");

/* encryption */
INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 1);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 2);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 3);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 4);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 5);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 6);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 7);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 8);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 9);

INSERT INTO encryption (e_cipher, e_id)
VALUES ("1234567890", 10);
