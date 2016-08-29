/* 
Confidential and Proprietary
(c) Copyright 1999 - 2009 Stratify, Inc. ( f/k/a PurpleYogi f/k/a Calpurnia ). All rights reserved.
The foregoing shall not be deemed to indicate that this source has been published. Instead, it remains a trade secret of Stratify, Inc.
*/

/***********************************************************************************************
*	This script will creat jobs at server level and it will loop through all the DBs for maintenance.
*	Please log in as "sa" to execute this job and this job includes contetx deletion, index maintenance
*	and annotation indexing for all the appropriate databases
***********************************************************************************************/

USE [msdb]
GO
/****** Object:  Job [#dba# - INDEX-MAINT-JOB]    Script Date: 03/23/2009 18:14:57 ******/
BEGIN TRANSACTION
DECLARE @ReturnCode INT
SELECT @ReturnCode = 0

--If the job exists, drop the job first and recreate here
IF EXISTS(SELECT TOP 1 1 FROM msdb..sysjobs WHERE name = N'#dba# - INDEX-MAINT-JOB')
BEGIN 
	EXECUTE msdb..sp_delete_job @job_name = N'#dba# - INDEX-MAINT-JOB'
END

IF NOT EXISTS (SELECT name FROM msdb.dbo.syscategories WHERE name=N'[MAINT_JOB]' AND category_class=1)
BEGIN
	EXEC @ReturnCode = msdb.dbo.sp_add_category @class=N'JOB', @type=N'LOCAL', @name=N'[MAINT_JOB]'
	IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
END

DECLARE @jobId BINARY(16)
EXEC @ReturnCode =  msdb.dbo.sp_add_job @job_name=N'#dba# - INDEX-MAINT-JOB', 
		@enabled=1, 
		@notify_level_eventlog=0, 
		@notify_level_email=0, 
		@notify_level_netsend=0, 
		@notify_level_page=0, 
		@delete_level=0, 
		@description=N'No description available.', 
		@category_name=N'[MAINT_JOB]', 
		@owner_login_name=N'sa', @job_id = @jobId OUTPUT
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
/****** Object:  Step [Main]    Script Date: 03/23/2009 18:14:57 ******/
EXEC @ReturnCode = msdb.dbo.sp_add_jobstep @job_id=@jobId, @step_name=N'Main', 
		@step_id=1, 
		@cmdexec_success_code=0, 
		@on_success_action=1, 
		@on_success_step_id=0, 
		@on_fail_action=2, 
		@on_fail_step_id=0, 
		@retry_attempts=0, 
		@retry_interval=0, 
		@os_run_priority=0, @subsystem=N'TSQL', 
		@command=N'EXEC sp_MSforeachdb ''Use ? DECLARE @errcode int, @errlabel varchar(max) 
					   IF EXISTS(SELECT TOP 1 1 FROM sys.parameters WHERE object_id = object_id(''''DBADMIN.DSP_DB_INDEX_MAINTENANCE'''') and name = ''''@errlabel'''')
					   BEGIN
							EXEC DBADMIN.DSP_DB_INDEX_MAINTENANCE @errcode = @errcode OUT, @errlabel = @errlabel OUT, @reporttable = ''''DBADMIN.DS_SYS_INDEX_PHYSICAL_STATS'''', @action = ''''REBUILD''''
					   END
					   ELSE IF OBJECT_ID(''''DBADMIN.DSP_DB_INDEX_MAINTENANCE'''') > 0 
					   BEGIN
							EXEC DBADMIN.DSP_DB_INDEX_MAINTENANCE @errcode = @errcode OUT, @reporttable = ''''DBADMIN.DS_SYS_INDEX_PHYSICAL_STATS'''', @action = ''''REBUILD''''
					   END''				   			   
				', 
		@database_name=N'master', 
		@flags=0
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_update_job @job_id = @jobId, @start_step_id = 1
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobschedule @job_id=@jobId, @name=N'#dba# - INDEX-MAINT-JOB', 
		@enabled=1, 
		@freq_type=4, 
		@freq_interval=1, 
		@freq_subday_type=1, 
		@freq_subday_interval=0, 
		@freq_relative_interval=0, 
		@freq_recurrence_factor=0, 
		@active_start_date=20090323, 
		@active_end_date=99991231, 
		@active_start_time=60000, 
		@active_end_time=235959
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobserver @job_id = @jobId, @server_name = N'(local)'
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
COMMIT TRANSACTION
GOTO EndSave
QuitWithRollback:
	IF (@@TRANCOUNT > 0) ROLLBACK TRANSACTION
EndSave:
GO