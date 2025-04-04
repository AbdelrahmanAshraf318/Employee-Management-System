CREATE OR REPLACE TRIGGER update_number_of_department
BEFORE INSERT OR DELETE on DEPARTMENT
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        UPDATE COMPANY
        SET NUMBER_OF_DEPARTMENTS = NUMBER_OF_DEPARTMENTS + 1
        WHERE ID = :NEW.company_id;
    ELSIF DELETING THEN
        UPDATE COMPANY
        SET NUMBER_OF_DEPARTMENTS = NUMBER_OF_DEPARTMENTS - 1
        WHERE ID = :OLD.company_id; 
    END IF;
END;
/