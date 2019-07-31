FUNCTION LIMIT_API_CALL(api-key,url,limit)
ts = CURRENT_UNIX_TIME()
keyname = api-key + ":" + url +":"+ ts
current = GET(keyname)
IF current != NULL AND current > limit THEN
    ERROR "too many requests per second"
ELSE
    MULTI
        INCR(keyname,1)
        EXPIRE(keyname,10)
    EXEC
    PERFORM_API_CALL()
END