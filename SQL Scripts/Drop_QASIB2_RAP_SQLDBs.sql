-- Stored Procedure to DROP RAP Databases.
-- @author: Avin
-- @Date: 21/06/2016

EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QASIB2'


EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QASIB2_ARCH'


EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QASIB2_queue'


USE [master]


ALTER DATABASE [QASIB2] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

ALTER DATABASE [QASIB2_ARCH] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

ALTER DATABASE [QASIB2_queue] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;


DROP DATABASE [QASIB2],[QASIB2_ARCH],[QASIB2_queue]

Print 'Databases [QASIB2],[QASIB2_ARCH] and [QASIB2_queue] have been deleted';