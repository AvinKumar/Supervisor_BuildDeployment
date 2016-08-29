-- Stored Procedure to DROP NP Databases.
-- @author: Avin
-- @Date: 21/06/2016

EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QASIB1'


EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'QASIB1_ARCH'


USE [master]


ALTER DATABASE [QASIB1] SET SINGLE_USER WITH ROLLBACK IMMEDIATE

ALTER DATABASE [QASIB1_ARCH] SET SINGLE_USER WITH ROLLBACK IMMEDIATE


DROP DATABASE [QASIB1],[QASIB1_ARCH]


Print 'Databases [QASIB1] and [QASIB1_ARCH] have been deleted' ;
