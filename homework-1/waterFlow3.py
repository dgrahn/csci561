from bfs import BFS
from dfs import DFS
from ucs import UCS
import operator
import sys

"""
A node in the graph.
"""
class Node(object):
  def __init__(self, name):
    self.name  = name
    self.paths = []
  #end __init__
  
  def children(self):
    children = []
    for path in self.paths:
      children.append(path.end)
      
    children.sort(key=operator.attrgetter("name"))
      
    return children
  #end children
  
  def cheap_paths(self, time):
    
    valid_paths = []
    
    for p in self.paths:
      if time in p.offtimes:
        continue
      valid_paths.append(p)

    valid_paths.sort(key=lambda x: (x.cost, x.end.name))
    
    return valid_paths
  #end cheap_children
#end Node

"""
A path between nodes.
"""
class Path(object):
  def __init__(self):
    self.start    = None
    self.end      = None
    self.cost     = 0
    self.offtimes = []
  #end ___init__
#end Path

"""
An entire task. Includes algorithm, source, destination, and nodes.
"""
class Task(object):

  def __init__(self, file):
    self.algorithm    = read_line(file)
    self.source       = Node(read_line(file))
    self.nodes        = {}
    self.pipes        = []
    self.destinations = []
    self.solution     = None
    self.end_time     = None

    # Add the source
    self.nodes[self.source.name] = self.source
    
    # Destinations
    for name in read_line(file).split():
    
      if name not in self.nodes:
        self.nodes[name] = Node(name)
        
      self.destinations.append(self.nodes[name])

    # -- Add the destinations
    for node in self.destinations:
      self.nodes[node.name] = node
    
    # -- Add the middle nodes
    for name in read_line(file).split():
      self.nodes[name] = Node(name)


    # Number of Pipes
    numberOfPipes = int(read_line(file))

    # Read Pipes
    for i in range(1, numberOfPipes + 1):
    
      path = Path()
      line = read_line(file).split()
      path.start = self.nodes[line.pop(0)]
      path.end   = self.nodes[line.pop(0)]
      path.cost  = int(line.pop(0))
      
      numberOfPeriods = int(line.pop(0))

      for _ in range(numberOfPeriods):
        period     = line.pop(0).split("-")
        start_time = int(period[0])
        end_time   = int(period[1])
        
        for i in range(start_time, end_time + 1):
          path.offtimes.append(i)
        #end
      #end
        
      path.offtimes.sort()
      path.start.paths.append(path)
      
    #end
      
    # Start Time
    self.start_time = int(read_line(file))

    # Separator Line
    read_line(file)
    
  #end __init__
  
  def __str__(self):
    line  = "Task:\n"
    line += "\tAlgorithm = " + self.algorithm + "\n"
    line += "\tSource = " + self.source.name + "\n"
    
    line += "\tDestinations = "
    
    for node in self.destinations:
      line += node.name + ", "
    line += "\n"
    
    line += "\tNodes:\n"
    for name in self.nodes:
      node = self.nodes[name]

      for path in node.paths:
        line += "\t\t" + path.start.name + " -> " + path.end.name + " - " + str(path.cost) +"\n"
        #line += "\t\t\t  Off = " + ", ".join(str(x) for x in path.offtimes) + "\n\n"

    if self.solution is None:
      line += "\tSolution = None\n"
    else:
      line += "\tSolution = " + ", ".join(n.name for n in self.solution) + "\n"

    line += "\tEnd Time = " + str(self.end_time) + "\n"
    line += "\tShort = " + self.short_solution() + "\n"
    return(line)
  #end __str__
  
  def short_solution(self):
    if self.solution is None:
      return "None"
    else:
      return self.solution[-1].name + " " + str(self.end_time)
  #end short_solution
  
  def run(self):
    if self.algorithm == "DFS":
      self.solution = DFS.run(self.source, self.destinations)
      if self.solution is not None:
        self.end_time = self.start_time + len(self.solution) - 1
        self.end_time = self.end_time % 24
    elif self.algorithm == "BFS":
      self.solution = BFS.run(self.source, self.destinations)
      if self.solution is not None:
        self.end_time = self.start_time + len(self.solution) - 1
        self.end_time = self.end_time % 24
    elif self.algorithm == "UCS":
      self.solution = UCS.run(self.start_time, self.source, self.destinations)
      if self.solution is not None:
        self.end_time = self.solution.pop(0)
    else:
      raise Exception("Unexpected Algorithm = " + self.algorithm)
  #end run

#end Task

def main(file):
  
  # Open the file
  file = open(file)
  output = open("output.txt", "w")
  
  # Read the number of tasks
  numberOfTasks = int(file.readline())
  
  for i in range(1, numberOfTasks + 1):
    try:
      task = Task(file)
      task.run()
      output.write(task.short_solution() + "\n")
      print(task.short_solution())
    except Exception:
      output.write("None\n")
      print("Err")
    
    
  # Close the file
  file.close()
  output.close()
  
#end main
    
def read_line(file):
  return file.readline().rstrip('\n')
#end readTask


if __name__ == "__main__":
  ", ".join(sys.argv)
  main(sys.argv[2])