/*
Alter script to modify scsp_GetSinglecastID so Singlecast ID generation will handle situation when hash 
generation criteria is changed and different hash can come in for the same message  
For SUP-6318 
*/
IF exists (SELECT name FROM sysobjects WHERE name = 'scsp_GetSinglecastID' AND type = 'P')
BEGIN
    PRINT CONVERT(VARCHAR, GETDATE(), 121) + ' - Dropping stored procedure scsp_GetSinglecastID'
    DROP PROCEDURE dbo.scsp_GetSinglecastID
END
GO

PRINT CONVERT(VARCHAR, GETDATE(), 121) + ' - Creating stored procedure scsp_GetSinglecastID'
GO
CREATE PROCEDURE dbo.scsp_GetSinglecastID
    @EduComServerID    varchar(100),
    @EduComMsgID       varchar(300),
    @Hash              varchar(100),
    @RequestID         int,
    @Action            int,
    @Status            int,
    @ProcessedDate     datetime,
    @isNightlyPush     int,
    @SinglecastID      BIGINT OUTPUT,
    @Created           int OUTPUT,
    @DedupError        int OUTPUT
AS
BEGIN
	SET NOCOUNT ON
	SET @SinglecastID = NULL
	SET @Created = 0
	SET @DedupError = 0

	DECLARE @Exist BIGINT
	DECLARE @chkHash varchar(100)

	SET @chkHash = NULL

	--This is a surveillance push
	IF (@isNightlyPush = 0)
	--T
	BEGIN
		--print 'T'
		--print 'surveillance'
		SET @SinglecastID = NULL
		SELECT @SinglecastID = SinglecastID 
		FROM ImportSinglecastIDGen
		WHERE EduComServerID = @EduComServerID AND EduComMsgID = @EduComMsgID
      
		--SinglecastID does not exist for this EduComServerID and EduComMsgID
		IF (@SinglecastID is NULL)
		--T.F
		BEGIN
			--print 'T.F'
            INSERT
            INTO ImportSinglecastIDGen(EduComServerID, EduComMsgID, RequestId, Hash, Action, Status, ProcessedDate, PerformBackfill)
            VALUES (@EduComServerID, @EduComMsgID, @RequestID, @Hash, @Action, @Status, @ProcessedDate, 0)
            
            SET @SinglecastID = SCOPE_IDENTITY()
            SET @Created = 1
		END
		--SinglecastID does exist for this EduComServerID and EduComMsgID, just return existing SinglecastID to overwrite old IDX
		ELSE
		--T.T
		BEGIN
			--print 'T.T'
			--If IDX has already been generated by nightly push, throw exception. Do not generate IDX
			IF EXISTS ( SELECT 1 FROM importsinglecastidgen i WHERE i.SinglecastID = @SinglecastID AND i.Action = 5)
			--T.T.T
			BEGIN
				--print 'T.T.T'
				SET @DedupError = 1
			END
			
			--Update ProcessedDate to make all it consistent in all locations
			IF (@DedupError = 0)
			--T.T.F
			BEGIN
				--print 'T.T.F'
				UPDATE ImportSinglecastIDGen SET ProcessedDate = @ProcessedDate WHERE SinglecastID = @SinglecastID 
			END
		END
	END
	--This is a nightly push
	--F
	ELSE
	BEGIN
		--print 'F'
		--print 'nightly'
		--Check if hash does not exist, dedup by zdk
		IF ( @Hash IS NULL)
		--F.T
		BEGIN
			--print 'F.T'
			SET @SinglecastID = NULL
			SELECT @SinglecastID = SinglecastID 
			FROM ImportSinglecastIDGen
			WHERE EduComServerID = @EduComServerID AND EduComMsgID = @EduComMsgID
			
			--If EduComServerID and EduComMsgID does not exist already, insert to table (means failure during surveillance push)
			IF (@SinglecastID is NULL)
			--F.T.F
			BEGIN
				--print 'F.T.F'
				INSERT
				INTO ImportSinglecastIDGen(EduComServerID, EduComMsgID, RequestId, Hash, Action, Status, ProcessedDate, PerformBackfill)
				VALUES (@EduComServerID, @EduComMsgID, @RequestID, @Hash, @Action, @Status, @ProcessedDate, 0)
		            
				SET @SinglecastID = SCOPE_IDENTITY()
				SET @Created = 1
			END
			--ZDK already exist
			ELSE
			--F.T.T
			BEGIN
				--print 'F.T.T'
				SET @Exist = NULL
				SELECT @Exist = ID
				FROM Singlecast S
				INNER JOIN ImportSinglecastIDGen ISIDG on s.ID = ISIDG.SinglecastID 
				WHERE ISIDG.EduComServerID = @EduComServerID AND ISIDG.EduComMsgID = @EduComMsgID
			
				--IF ID already exist in singlecast table, throw an exception
				IF (@Exist is not NULL)
				--F.T.T.T
				BEGIN
					--print 'F.T.T.T'
					--There is a dedup exception by ZDK
					SET @DedupError = 1
				END 
				--Update RequestID, ProcessedDate, return ID, insert to...
				ELSE
				--F.T.T.F
				BEGIN
					--print 'F.T.T.F'
					UPDATE ImportSinglecastIDGen SET RequestID = @RequestID, ProcessedDate = @ProcessedDate WHERE SinglecastID = @SinglecastID
				END 
			END 
		END
		--hash does exist, dedup by hash
		ELSE
		--F.F
		BEGIN
			--print 'F.F'
			SET @SinglecastID =  NULL
			SET @chkHash = NULL
			SELECT @SinglecastID = SinglecastID, @chkHash = Hash
			FROM ImportSinglecastIDGen
			WHERE EduComServerID = @EduComServerID AND EduComMsgID = @EduComMsgID
			
			--If EduComServerID and EduComMsgID does not exist already, check if hash exist
			IF (@SinglecastID is NULL)
			--F.F.F
			BEGIN
				--print 'F.F.F'
				SET @Exist = NULL
				SELECT @Exist = SINGLECASTID
				FROM ImportSinglecastIDGEN
				WHERE hash = @Hash
			
				--IF hash does not exist
				IF (@Exist is NULL)
				--F.F.F.F
				BEGIN
					--print 'F.F.F.F'
					SET @SinglecastID = NULL
					SELECT @SinglecastID = SinglecastID 
					FROM ImportSinglecastIDGen
					WHERE EduComServerID = @EduComServerID AND EduComMsgID = @EduComMsgID
			
					--If EduComServerID and EduComMsgID does not exist already, insert to table (means failure during surveillance push)
					IF (@SinglecastID is NULL)
					BEGIN
						INSERT
						INTO ImportSinglecastIDGen(EduComServerID, EduComMsgID, RequestId, Hash, Action, Status, ProcessedDate, PerformBackfill)
						VALUES (@EduComServerID, @EduComMsgID, @RequestID, @Hash, @Action, @Status, @ProcessedDate, 0)
		            
						SET @SinglecastID = SCOPE_IDENTITY()
						SET @Created = 1
					END
				END	
				ELSE
				--F.F.F.T
				BEGIN
				--print 'F.F.F.T'
					--There is a dedup exception by hash
					SET @DedupError = 2 
				END
			END
			ELSE
			--F.F.T
			BEGIN
				--print 'F.F.T'
				SET @Exist = NULL
				SELECT @Exist = SINGLECASTID
				FROM ImportSinglecastIDGEN
				WHERE hash = @Hash
			
				--IF passed in hash, @Hash, does not exist in ImportSinglecastIDGen
				IF (@Exist is NULL)
				--F.F.T.F
				BEGIN
					--IF a hash exist in ImportSinglecastIDGen table for the passed in @EduComServerID and @EduComMsgID but is not equal to the passed in @Hash
					IF (@chkHash <> @Hash)
					--print 'F.F.T.F.T'
					BEGIN
						--Hash value might have changes because dedup criteria might have been set, message was already pushed so dedup by ZDK
						SET @DedupError = 3
					END
					ELSE
					--print 'F.F.T.F.F'
					BEGIN
						--If ID does not exist already, insert to table (means failure during surveillance push)
						UPDATE ImportSinglecastIDGen SET RequestID = @RequestID, ProcessedDate = @ProcessedDate, hash = @Hash WHERE SinglecastID = @SinglecastID
					END 
				END
				ELSE
				--F.F.T.T
				BEGIN
					--print 'F.F.T.T'
					--There is a dedup exception by hash
					SET @DedupError = 2 
				END
			END
		END
	END 
	SET NOCOUNT OFF
END
GO