package com.cmd.exchange.common.model;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_task_server
 *
 * @mbg.generated do_not_delete_during_merge Thu Jun 21 10:03:28 CST 2018
 */
public class TaskServer {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_server.id
     *
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    private Integer id;

    /**
     * Database Column Remarks:
     * 后台任务名称
     * <p>
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_server.task_name
     *
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    private String taskName;

    /**
     * Database Column Remarks:
     * 进行该工作的服务器地址
     * <p>
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_server.server_address
     *
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    private String serverAddress;

    /**
     * Database Column Remarks:
     * 最近一次检测的时间
     * <p>
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_server.last_update_time
     *
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    private Integer lastUpdateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task_server.id
     *
     * @return the value of t_task_server.id
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task_server.id
     *
     * @param id the value for t_task_server.id
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task_server.task_name
     *
     * @return the value of t_task_server.task_name
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task_server.task_name
     *
     * @param taskName the value for t_task_server.task_name
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task_server.server_address
     *
     * @return the value of t_task_server.server_address
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task_server.server_address
     *
     * @param serverAddress the value for t_task_server.server_address
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress == null ? null : serverAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task_server.last_update_time
     *
     * @return the value of t_task_server.last_update_time
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public Integer getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task_server.last_update_time
     *
     * @param lastUpdateTime the value for t_task_server.last_update_time
     * @mbg.generated Thu Jun 21 10:03:28 CST 2018
     */
    public void setLastUpdateTime(Integer lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}