SET QUOTED_IDENTIFIER OFF
SET NOCOUNT ON
use master
--Declaring the variable
DECLARE @RESULT INT
DECLARE @DBNAME nvarchar(255)
DECLARE @DISK_PATH nvarchar(1024)
DECLARE @DBHOME nvarchar(1024)
DECLARE @LOGPATH nvarchar(1024)
DECLARE @RESTORE_SQL nvarchar(max)
DECLARE @SQL1 nvarchar(512)
DECLARE @SQL nvarchar(255)

--Setting up values
SET @DBNAME='Manu_10'
SET @DISK_PATH='E:\sqldb\Manu_10\Manu_10.BAK'
SET @DBHOME='E:\sqldb\Manu_10\DB'
SET @LOGPATH='F:\sqldb\Manu_10\LOGS'

--Checking the existence of given path
IF OBJECT_ID('tempdb..#TMP_CHCK_DIRECTORY') IS NOT NULL
drop table #TMP_CHCK_DIRECTORY

CREATE TABLE #TMP_CHCK_DIRECTORY
(
	OUTPUT VARCHAR(200) NULL
)

-- Check the existence of the directory and give msg, if it does not exist
SET @SQL = 'dir ' + @DISK_PATH
print @DISK_PATH
--drop table #TMP_CHCK_DIRECTORY
	INSERT INTO #TMP_CHCK_DIRECTORY
		EXEC  master..xp_cmdshell @SQL
		print @RESULT
		select @RESULT=COUNT(*) from #TMP_CHCK_DIRECTORY WHERE OUTPUT LIKE 'File Not Found'
if @RESULT<>0
	BEGIN
		print @RESULT
		print 'Backup DB file is not available...pls check and Retry'
		delete from #TMP_CHCK_DIRECTORY
		SET @SQL =''
		RETURN
	END

--checking DB Home path
truncate table #TMP_CHCK_DIRECTORY
SET @SQL='dir '+@DBHOME

	INSERT INTO #TMP_CHCK_DIRECTORY
		EXEC  master..xp_cmdshell @SQL
	print @RESULT
	select @RESULT=COUNT(*) from #TMP_CHCK_DIRECTORY WHERE OUTPUT LIKE 'File Not Found'
if @RESULT<>0
	BEGIN
		print @RESULT
		print 'Database Home Path does not exist...pls check and Retry'
		delete from #TMP_CHCK_DIRECTORY
		SET @SQL =''
		RETURN
	END

--checking LOG path
truncate table #TMP_CHCK_DIRECTORY
SET @SQL='dir '+@LOGPATH

	INSERT INTO #TMP_CHCK_DIRECTORY
		EXEC  master..xp_cmdshell @SQL
	print @RESULT
	select @RESULT=COUNT(*) from #TMP_CHCK_DIRECTORY WHERE OUTPUT LIKE 'File Not Found'
if @RESULT<>0
	BEGIN
		print @RESULT
		print 'Database LOG Path does not exist...pls check and Retry'
		delete from #TMP_CHCK_DIRECTORY
		SET @SQL =''
		RETURN
	END



--Starting restoring the database
IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = @DBNAME) 
BEGIN 
SET @RESTORE_SQL='RESTORE DATABASE '+'['+@DBNAME+']'+' FROM DISK = '+"'"+@DISK_PATH+"'"
 + ' WITH MOVE '+"'PRIMARY'"+' TO '+"'"+@DBHOME+'\PRIMARY.mdf'+"',"
 +	'MOVE '+"'STRATIFY_log'"+' TO '+"'"+@LOGPATH+'\Stratify_log.ldf'+"',"
 +	'MOVE '+"'CLASSIFICATION_DATA'"+' TO '+"'"+@DBHOME+'\CLASSIFICATION_DATA.ndf'+"',"
 +	'MOVE '+"'CLASSIFICATION_INDEX'"+' TO '+"'"+@DBHOME+'\CLASSIFICATION_INDEX.ndf'+"',"
 +	'MOVE '+"'CONFIG'"+' TO '+"'"+@DBHOME+'\CONFIG.ndf'+"',"
 +	'MOVE '+"'CONTEXT' "+' TO '+"'"+@DBHOME+'\CONTEXT.ndf'+"',"
 +	'MOVE '+"'HISTORY_DATA'"+' TO '+"'"+@DBHOME+'\HISTORY_DATA.ndf'+"',"
 +	'MOVE '+"'HISTORY_INDEX'"+' TO '+"'"+@DBHOME+'\HISTORY_INDEX.ndf'+"',"
 +	'MOVE '+"'EXTRACTION_DATA'"+' TO '+"'"+@DBHOME+'\EXTRACTION_DATA.ndf'+"',"
 +	'MOVE '+"'EXTRACTION_INDEX'"+' TO '+"'"+@DBHOME+'\EXTRACTION_INDEX.ndf'+"',"
 +	'MOVE '+"'METADATA_DATA'"+' TO '+"'"+@DBHOME+'\METADATA_DATA.ndf'+"',"
 +	'MOVE '+"'METADATA_INDEX'"+' TO '+"'"+@DBHOME+'\METADATA_INDEX.ndf'+"',"
 +	'MOVE '+"'MIGRATION'"+' TO '+"'"+@DBHOME+'\MIGRATION.ndf'+"',"
 +	'MOVE '+"'MODEL_DATA'"+' TO '+"'"+@DBHOME+'\MODEL_DATA.ndf'+"',"
 +	'MOVE '+"'MODEL_INDEX'"+' TO '+"'"+@DBHOME+'\MODEL_INDEX.ndf'+"',"
 +	'MOVE '+"'NORMALIZATION'"+' TO '+"'"+@DBHOME+'\NORMALIZATION.ndf'+"',"
 +	'MOVE '+"'PRODUCTION'"+' TO '+"'"+@DBHOME+'\PRODUCTION.ndf'+"',"
 +	'MOVE '+"'SECURITY'"+' TO '+"'"+@DBHOME+'\SECURITY.ndf'+"',"
 +	'MOVE '+"'SYSTEM'"+' TO '+"'"+@DBHOME+'\SYSTEM.ndf'+"',"
 +	'MOVE '+"'TOPIC'"+' TO '+"'"+@DBHOME+'\TOPIC.ndf'+"',"
 +	'MOVE '+"'WORKBENCH'"+' TO '+"'"+@DBHOME+'\WORKBENCH.ndf'+"'"

print @RESTORE_SQL

	print 'Restoring the database....'
	execute sp_executesql @RESTORE_SQL
	print ''
	print 'Database is successfully restored'

--Mapping Orphan users

DECLARE @USERNAME nvarchar(255)
DECLARE @ORPHAN_SQL nvarchar(1024)


SET QUOTED_IDENTIFIER OFF

--dropping the temp table if already exists 
if OBJECT_ID('tempdb..#Orphan_Users') IS NOT NULL
drop table tempdb..#Orphan_Users

create table #Orphan_Users (username nvarchar(255),userSID varbinary(MAX))


--Getting the ORPHAN users if exists

SET @SQL=''
SET @SQL='insert into '+@DBNAME+'..'+'#Orphan_Users '+'EXEC '+@DBNAME+'..'+'sp_change_users_login @Action='+"'Report'"
print @SQL

execute sp_executesql @SQL



--SHUBHENDU--------------

SELECT @USERNAME=''
--print @USERNAME


while @USERNAME<=(select max(username) from #Orphan_Users)
BEGIN 
--print @USERNAME

if @USERNAME<>''
      BEGIN TRY
                  SET @SQL='EXEC '+@DBNAME+'..'+'sp_change_users_login '+ "'"+'Update_One'+"'"+","+"'"+ @USERNAME+"'"+","+"'"+@USERNAME+"'"
                  print @SQL               
                  execute sp_executesql @SQL
      END TRY
      BEGIN CATCH
                  print ERROR_MESSAGE()
      END CATCH

SELECT @USERNAME=min(username) from #Orphan_Users where username>@USERNAME
print @USERNAME
END

SET QUOTED_IDENTIFIER ON

------------------------End Of Orphan Users Mapping------------------------------


--setting security for the restored database
	print 'Setting Trustworthy ON....'
	SET @SQL1='ALTER DATABASE '+@DBNAME + ' SET TRUSTWORTHY ON'

	execute sp_executesql @SQL1
END
ELSE
Print 'Database already exists..'


SET QUOTED_IDENTIFIER ON
SET NOCOUNT ON


