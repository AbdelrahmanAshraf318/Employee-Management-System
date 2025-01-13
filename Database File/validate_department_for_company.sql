CREATE OR REPLACE TRIGGER validate_department_for_company
BEFORE INSERT OR UPDATE ON EMPLOYEE
FOR EACH ROW
DECLARE
    v_company_id NUMBER;
BEGIN
    SELECT company_id INTO v_company_id
    FROM DEPARTMENT
    WHERE department_id = :NEW.department_id;
    
    IF v_company_id != :NEW.company_id THEN
        RAISE_APPLICATION_ERROR(-20001, 'The department does not belong to the selected company.');
    END IF;

END;
/
    