Feature: Employees can be retrieved
    Scenario: client makes call to GET /employees
        When the client calls /employees
        Then the client receives response status code of 200