-------------------------------------------------------------------------------------------------------
-- the following insert statements populate some data for the Javalin Bank
-------------------------------------------------------------------------------------------------------

--1  ADDTING THE MAIN 2 ROLES
insert into Bank_roles (role) values ('ROLE_ADMIN'), ('ROLE_USER');

--2  ADDING A NEW USER
insert into Bank_user_accounts (username, password, roles_id, email) values ('chuck.Lee', 'p@$$w0rd123', 1, 'chuck.lee@revature.net');
--3  ADDING A BANK ACCOUNT FOR A SPECIFC USER
INSERT INTO bank_account  ( user_id , accounttype , accountopen)
SELECT id , 'CHECKING',true from bank_user_accounts where username = 'chris.jones'
;
--4  GET ALL BANK ACCOUNTS FOR A SPECIFC USER
SELECT * from bank_account ba
where (user_id = 3 and accountopen = true )
;
--CLOSE AN ACCOUNT
update bank_account ba
set accountopen  = false
where accountnumber = '773327'
;
---------------------------------------------------------------------------------------------------------
-- ADDING NEW ACCOUNT FOR A NEW USER AND MAKING INITIAL DEPOSIT
WITH ins1 AS (
   INSERT INTO bank_user_accounts (username, password, email )
   VALUES ('jack.jack', 'p@$$w0rd123' ,  'jack.schuwan@revature.net')
 --ON     CONFLICT DO NOTHING         -- optional addition in Postgres 9.5+
   RETURNING id AS assignedaccountid
   )
, ins2 AS (
   INSERT INTO bank_account  ( user_id , accounttype , accountopen)
   SELECT assignedaccountid, 'CHECKING',true FROM ins1
   RETURNING accountnumber as assignedaccountnumber
   )
 INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
  SELECT assignedaccountnumber, CURRENT_DATE, 'deposit', 'Account open-- initial deposit processed in person',1000 FROM ins2
 ;
---------------------------------------------------------------------------------------------------------
-- ADDING NEW ACCOUNT FOR AN EXISTING USER AND MAKING INITIAL DEPOSIT
WITH ins2 AS (
   INSERT INTO bank_account  ( user_id , accounttype , accountopen)
   SELECT id , 'SAVINGS',true from bank_user_accounts where username = 'jill.brown'
   RETURNING accountnumber as assignedaccountnumber
   )
 INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
  SELECT assignedaccountnumber, CURRENT_DATE, 'deposit', 'Account open-- initial deposit processed in person',1000 FROM ins2
 ;
---------------------------------------------------------------------------------------------------------
-- ADDING A TRANSACTION FOR A GIVEN USER IN A GIVEN ACCOUNT
  WITH ins1 AS (
  SELECT accountnumber as assignedaccountnumber_s from bank_account
  	where user_id = (SELECT id  from bank_user_accounts where username = 'jill.brown')

  ), ins2 as (
  select assignedaccountnumber_s as assignedaccountnumber from ins1
  	where assignedaccountnumber_s = '323580' -- THE USER HAS ALREADY CHOSEN WHICH ACCOUNT HE is WORKIGN ON
  )
    INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
  select assignedaccountnumber ,  CURRENT_DATE, 'withdrawal', 'ATM at the corner of Jericho Tpke west in Nassau county, NY',2200 FROM ins2
  ;
---------------------------------------------------------------------------------------------------------
-- TRANSFER OUT AMOUNT TO ANOTHER ACCOUNT (2 TRANSACTIONS) FOR A GIVEN USER IN A GIVEN ACCOUNT ----->>>>>>>>>>>>>>>>>>>>-TESTING
--1
  WITH ins1 AS (
  SELECT accountnumber as assignedaccountnumber_s from bank_account
  	where user_id = (SELECT id  from bank_user_accounts where username = 'jill.brown')
  )
  , ins2 as (
  select assignedaccountnumber_s as assignedaccountnumber from ins1
  	where assignedaccountnumber_s = '323580' -- THE USER HAS ALREADY CHOSEN WHICH ACCOUNT HE is WORKIGN ON
  )
  , ins3 as (
    INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
  select assignedaccountnumber ,  CURRENT_DATE, 'transfer-out', concat('funds transfer to account # ','999999',' ->NOTE'),4321 FROM ins2
  returning id as transfer_out_id, amount as transfer_out_amount
  )
 INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
	 select '999999' ,  CURRENT_DATE,'transfer-in', concat('funds transfer from account # ','323580',' ->NOTE') , transfer_out_amount from ins3
;

---------------------------------------------------------------------------------------------------------
-- Filling out A NEW LOAN REQUEST FOR AN EXISTING USER

INSERT INTO bank_loanrequest (date, description, review, amount, user_id, loanaccountnumber)
	values (CURRENT_DATE, 'a 5 year car loan for a new Tesla model P', false, 20000,  (SELECT id  from bank_user_accounts where username = 'jill.brown'), 'NOACCOUNT')
;
---------------------------------------------------------------------------------------------------------
-- APPROVE A NEW LOAN ACCOUNT FOR AN EXISTING USER AND MAKING INITIAL DEPOSIT
---------------------------------------------------------------------------------------------------------

--FIND THE LOAN REQUEST
select * from bank_loanrequest bl
	where loanaccountnumber ='NOACCOUNT'
		--and id = (select max(id) from bank_loanrequest)
		and user_id in (SELECT id  from bank_user_accounts where username = 'jill.brown')
;

--REVIEW THE LOANREQUEST
WITH ins1 AS (
   INSERT INTO bank_creditcheck (date) values(CURRENT_DATE)
   returning id as assignedscoreid
   )
   update bank_loanrequest
   set creditscore_id = assignedscoreid from ins1
   WHERE loanaccountnumber = 'NOACCOUNT'
		and user_id in (SELECT id  from bank_user_accounts where username = 'jill.brown')
   ;

 -- REJECT THE LOAN REQUEST
  update bank_loanrequest
  set approved = false,
  		review = true
  WHERE loanaccountnumber = 'NOACCOUNT'
		and user_id in (SELECT id  from bank_user_accounts where username = 'jill.brown')
   ;
--OR PROVIDE AN INTREST RATE OBTAIN A CREDITSCOREID AND UPDATE THE AOUMNT TO REFLECT THE LOAN GIVEN
  update bank_loanrequest
  set interestrate  = .05,
  		review = true,
  		amount = 8000
  WHERE loanaccountnumber = 'NOACCOUNT'
		and user_id in (SELECT id  from bank_user_accounts where username = 'jill.brown')
   ;

-- ADD A NEW LOAN ACCOUNT FOR THE USER AND APPLY INITIAL LIABILITY
WITH ins2 AS (
   INSERT INTO bank_account  ( user_id , accounttype , accountopen)
   SELECT id , 'LOAN',true from bank_user_accounts where username = 'jill.brown'
   RETURNING accountnumber as assignedaccountnumber
   )
 INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
  SELECT assignedaccountnumber, CURRENT_DATE, 'liability', 'Loan Account approved--',
  (select amount from bank_loanrequest where creditscore_id = (select max(creditscore_id) from bank_loanrequest))FROM ins2 -- THE EMPLOYEE WILL PROVIDE THE ATUAL AMOUNT GIVEN as LOAN
 ;

--UPDATE THE LOANREQUEST TO REFLECT LOANREQUEST ACCOUNTNUMBER,PROPER DESCTIPTION
UPDATE bank_loanrequest
	SET description = concat(description ,'.  loan approved, interest will be applied to your loan account every month'),
		loanaccountnumber = (select accountnumber from bank_transactions where type = 'liability' and id = (select max(id) from bank_transactions) ),
		approved = true
	where  creditscore_id in (select max(creditscore_id) from bank_loanrequest )
;

-- APPLY A NEW DEPOSIT (FOR THE LOAN AMOUNT) TO THE FIRST EXISTING CHECKING ACCOUNT FOR THE USER
 WITH ins1 AS (
  SELECT accountnumber as assignedaccountnumber_s from bank_account
  	where user_id = (SELECT id  from bank_user_accounts where username = 'jill.brown')
  	)
  , ins2 as (
  select assignedaccountnumber_s as assignedaccountnumber from ins1
  	where assignedaccountnumber_s =( select accountnumber from bank_account where id in (select min(id) from bank_account))
  	)
    INSERT INTO bank_transactions  (accountnumber , date, type, description, amount)
	  select assignedaccountnumber ,  CURRENT_DATE,'deposit', 'Loan approved-- deposit of principal amount',
	  	(select amount from bank_loanrequest where creditscore_id in (select max(creditscore_id) from bank_loanrequest )) from ins2
;

---------------------------------------------------------------------------------------------------------
 -- REQUIRED QUERIES
---------------------------------------------------------------------------------------------------------
--GET ALL TRANSACTIONS IN THE SYSTEM
select * from bank_user_accounts
	left join bank_account on bank_user_accounts.id = bank_account.user_id
	left join bank_transactions on bank_account.accountnumber = bank_transactions.accountnumber
;
--GET ALL TRANSACTIONS FOR MYSELF THE USER FOR MY CURRENT ACCOUNT IN USE / User
select * from bank_user_accounts
	left join bank_account on bank_user_accounts.id = bank_account.user_id
	left join bank_transactions on bank_account.accountnumber = bank_transactions.accountnumber
	where Bank_user_accounts.username = 'jill.brown' and bank_account.accountnumber = '323580'
;
--GET ALL TRANSACTIONS FOR ANY USER FOR A SPECIFIC ACCOUNT - Admin / Employee
select * from bank_user_accounts
	left join bank_account on bank_user_accounts.id = bank_account.user_id
	left join bank_transactions on bank_account.accountnumber = bank_transactions.accountnumber
	where Bank_user_accounts.username = 'jack.jack' and bank_account.accountnumber = '66899'
;
-- GET CREDENTIALS -1
SELECT id, username, roles_id from bank_user_accounts where (username = 'jill.brown');
-- GET CREDENTIALS -2
SELECT accountnumber, accounttype from bank_account where (user_id = 3 and accountopen = true);