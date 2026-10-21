import subprocess, json, time, os, select
p = subprocess.Popen(["/opt/icons8/lunacy/Lunacy","-mcp"],
                     stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.DEVNULL,
                     cwd=os.getcwd(), bufsize=0)
def send(o):
    p.stdin.write((json.dumps(o)+"\n").encode()); p.stdin.flush()
def read_until(mid, timeout=25):
    deadline=time.time()+timeout; buf=b""
    while time.time()<deadline:
        r,_,_=select.select([p.stdout],[],[],1)
        if r:
            c=os.read(p.stdout.fileno(),65536)
            if not c: break
            buf+=c
            if f'"id":{mid}'.encode() in buf: break
    out=[]
    for line in buf.decode(errors="replace").splitlines():
        try: o=json.loads(line)
        except: continue
        if o.get("id")==mid: out.append(o)
    return out

send({"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"probe","version":"1"}}})
time.sleep(4); send({"jsonrpc":"2.0","method":"notifications/initialized"}); time.sleep(2)

send({"jsonrpc":"2.0","id":10,"method":"tools/call","params":{"name":"get_viewport_info","arguments":{}}})
r=read_until(10,20)
print("=== get_viewport_info ===")
print(json.dumps(r, ensure_ascii=False)[:1500])

send({"jsonrpc":"2.0","id":11,"method":"tools/call","params":{"name":"get_selected","arguments":{}}})
r=read_until(11,20)
print("\n=== get_selected ===")
print(json.dumps(r, ensure_ascii=False)[:1500])
p.kill()
