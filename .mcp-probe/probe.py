import subprocess, json, sys, time, os, select
p = subprocess.Popen(["/opt/icons8/lunacy/Lunacy","-mcp"],
                     stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.DEVNULL,
                     cwd=os.getcwd(), bufsize=0)
def send(o):
    p.stdin.write((json.dumps(o)+"\n").encode()); p.stdin.flush()
send({"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"probe","version":"1"}}})
time.sleep(4)
send({"jsonrpc":"2.0","method":"notifications/initialized"})
time.sleep(2)
send({"jsonrpc":"2.0","id":2,"method":"tools/list"})
deadline=time.time()+25; buf=b""
while time.time()<deadline:
    r,_,_=select.select([p.stdout],[],[],1)
    if r:
        c=os.read(p.stdout.fileno(),65536)
        if not c: break
        buf+=c
        if b'"id":2' in buf: break
print("RECEIVED BYTES:",len(buf))
print(buf.decode(errors="replace")[:4000])
p.kill()
