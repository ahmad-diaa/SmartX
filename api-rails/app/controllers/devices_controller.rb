
class DevicesController < ApplicationController
  def find 
    @user = User.find(params[:user_id])
    @room = @user.rooms.find(params[:room_id])
    @device = @room.devices.where(:name => params[:name])
    render json: @device if stale?(etag: @device.all, last_modified: @device.maximum(:updated_at))
  end
  #Returns list of devices in a specific room knowing she belongs to which user. 
  # GET /devices
  # GET /devices.json
  def index
    @user=  User.find(params[:user_id])
    @room = @user.rooms.find(params[:room_id])
    @devices = @room.devices.all
    render json: @devices if stale?(etag: @devices.all, last_modified: @devices.maximum(:updated_at))
  end

  #Returns device with given id knowing he belongs to which user and room.
  # GET /devices/1
  # GET /devices/1.json
  def show
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device= @room.devices.find(params[:device_id])
    render json: @device if stale?(@device)
  end

  #Creates a new device in a specific room knowing she belongs to which user.
  # POST /devices
  # POST /devices.json
  def create
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device= @room.devices.create(device_params)
    @device.user_id= @room.user_id
    if @device.save
      render json: @device, status: :created
    else
      render json: @device.errors, status: :unprocessable_entity
    end
  end

  #Updates device parameters with given id knowing he belongs to which user and room.
  # PATCH/PUT /devices/1
  # PATCH/PUT /devices/1.json
  def update
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device= @room.devices.find(params[:device_id])
    if @device.update(device_params)
      @device.status = params[:status]
      head :no_content
    else
      render json: @device.errors, status: :unprocessable_entity
    end
  end
  
  #Gets all devices that belong to a user. 
#GET /devices/users/1/rooms/1/devices/1/devices
def get_all_type
    @devices = Device.all
    @user=User.find(params[:user_id])
    render json: @devices if stale?(etag: @devices.all, last_modified: @devices.maximum(:updated_at))
end  

  #Deletes device with given id knowing he belongs to which user and room.
  # DELETE /devices/1
  # DELETE /devices/1.json
  def destroy
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device= @room.devices.find(params[:name])
    @device.destroy
    head :no_content
  end

  private
  # Never trust parameters from the scary internet, only allow the white list through.
  def device_params
    params.require(:device).permit(:name,:device_id,:user_id,:room_id, :status)
  end
end
