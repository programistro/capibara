#!/usr/bin/env python3
"""Minimal MCP client for Lunacy (-mcp stdio server). Usage: lunacy.py <tool> '<json-args>'"""
import subprocess, json, sys, time, os, select, base64

LUNACY = "/opt/icons8/lunacy/Lunacy"

class Lunacy:
    def __init__(self):
        self.p = subprocess.Popen([LUNACY, "-mcp"], stdin=subprocess.PIPE,
                                  stdout=subprocess.PIPE, stderr=subprocess.DEVNULL,
                                  bufsize=0)
    def _send(self, o):
        self.p.stdin.write((json.dumps(o) + "\n").encode()); self.p.stdin.flush()
    def _read(self, mid, timeout=60):
        deadline = time.time() + timeout; buf = b""
        while time.time() < deadline:
            r, _, _ = select.select([self.p.stdout], [], [], 1)
            if r:
                c = os.read(self.p.stdout.fileno(), 1 << 20)
                if not c: break
                buf += c
                if f'"id":{mid}'.encode() in buf: break
        for line in buf.decode(errors="replace").splitlines():
            try: o = json.loads(line)
            except Exception: continue
            if o.get("id") == mid: return o
        return None
    def start(self):
        self._send({"jsonrpc":"2.0","id":1,"method":"initialize","params":{
            "protocolVersion":"2024-11-05","capabilities":{},
            "clientInfo":{"name":"dsh-bridge","version":"1"}}})
        self._read(1, 30); time.sleep(1)
        self._send({"jsonrpc":"2.0","method":"notifications/initialized"}); time.sleep(1)
    def call(self, tool, args=None, timeout=90):
        self._send({"jsonrpc":"2.0","id":2,"method":"tools/call",
                    "params":{"name":tool,"arguments":args or {}}})
        return self._read(2, timeout)
    def close(self):
        try: self.p.kill()
        except Exception: pass

def render(resp, save_image=None):
    if resp is None: return "NO RESPONSE (timeout)"
    if "error" in resp: return "ERROR: " + json.dumps(resp["error"], ensure_ascii=False)
    r = resp.get("result", {}); parts = []
    for c in r.get("content", []):
        if c.get("type") == "text": parts.append(c["text"])
        elif c.get("type") == "image":
            raw = base64.b64decode(c["data"])
            if save_image:
                open(save_image, "wb").write(raw)
                parts.append(f"[image saved: {save_image} ({len(raw)} bytes, {c.get('mimeType')})]")
            else: parts.append(f"[image {len(raw)} bytes, {c.get('mimeType')}]")
        else: parts.append(json.dumps(c, ensure_ascii=False)[:500])
    out = "\n".join(parts) or "(empty)"
    if r.get("isError"): out = "TOOL ERROR: " + out
    return out

if __name__ == "__main__":
    tool = sys.argv[1]
    args = json.loads(sys.argv[2]) if len(sys.argv) > 2 else {}
    save = None
    for a in sys.argv[3:]:
        if a.startswith("--save="): save = a.split("=", 1)[1]
    lc = Lunacy(); lc.start()
    try: print(render(lc.call(tool, args), save))
    finally: lc.close()
