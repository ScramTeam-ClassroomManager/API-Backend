package it.unical.classroommanager_api.dto;

import lombok.Data;

@Data
public class CubeDto {
    private int number;
    private long departmentId;
    private DepartmentDto department;

    public CubeDto() {
    }

    public CubeDto(int number, DepartmentDto department) {
        this.number = number;
        this.department = department;
        if (department != null) {
            this.departmentId = department.getId();
        }
    }

    public CubeDto(int number, long departmentId) {
        this.number = number;
        this.departmentId = departmentId;
    }

    public long getDepartmentId() {
        if (department != null) {
            return department.getId();
        }
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
        this.department = null;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
        if (department != null) {
            this.departmentId = department.getId();
        }
    }
}
