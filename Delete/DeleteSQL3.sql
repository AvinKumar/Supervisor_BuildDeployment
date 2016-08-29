-- Stored Procedure to DROP NP Databases.
-- @author: Avin
-- @Date: 03/06/2016

EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'avin1'
EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'avin2'


USE [master]


DROP DATABASE [avin1],[avin2]
Print 'Databases [avin1] and [avin2] have been deleted' ;
