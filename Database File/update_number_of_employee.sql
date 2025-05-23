CREATE OR REPLACE TRIGGER update_number_of_employee
BEFORE INSERT OR DELETE on EMPLOYEE
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        UPDATE COMPANY
        SET NUMBER_OF_EMPLOYEES = NUMBER_OF_EMPLOYEES + 1
        WHERE ID = :NEW.company_id;
    ELSIF DELETING THEN
        UPDATE COMPANY
        SET NUMBER_OF_EMPLOYEES = NUMBER_OF_EMPLOYEES - 1
        WHERE ID = :OLD.company_id; 
    END IF;
END;
/
