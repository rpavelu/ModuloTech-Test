# ModuloTech Test Application

A small smart home application, able to steer devices such as lights, roller shutters, or heaters.

## Description

- Fetch and parse the file http://storage42.com/modulotest/data.json
- On the home page, list of devices filterable by product type
  - List of devices of one or more selected device types
  - Be able to delete any device
- A steering page for each device type:
  - Lights: Mode ON/OFF and intensity management (0 - 100)
  - Roller shutters: Set position using a vertical slider (0 - 100)
  - Heaters: Mode ON/OFF and set the temperature with a step of 0.5 degrees (min: 7°, max 28°)
- A user profile page where we can update all the information. Fields is handled with an error management (email pattern, empty field, ...)
