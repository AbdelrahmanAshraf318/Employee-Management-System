CREATE OR REPLACE TRIGGER calculate_salary
BEFORE INSERT OR UPDATE on EMPLOYEE
FOR EACH ROW
BEGIN
    :NEW.salary := :NEW.working_hours * :NEW.hourly_rate;
END;
/