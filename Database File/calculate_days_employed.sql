CREATE OR REPLACE TRIGGER calculate_days_employed
BEFORE INSERT or UPDATE on EMPLOYEE
FOR EACH ROW
BEGIN
    IF :NEW.HIRED_ON IS NOT NULL THEN
        :NEW.DAYS_EMPLOYED := TRUNC(SYSDATE) - TRUNC(:NEW.HIRED_ON);
    ELSE
        :NEW.DAYS_EMPLOYED := NULL;
    END IF;
END;
/

