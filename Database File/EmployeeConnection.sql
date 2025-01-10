CREATE OR REPLACE TRIGGER update_days_employed
BEFORE INSERT OR UPDATE on Employee
FOR EACH ROW
BEGIN
    IF :NEW.hiredOn IS NOT NULL THEN
        :NEW.daysEmployed := TRUNC(SYSDATE) - TRUNC(:NEW.hiredOn);
    END IF;
END;
/
    
