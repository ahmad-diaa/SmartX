class PlugsController < ApplicationController

	def index
    @user=  User.find(params[:user_id])
    @room = @user.rooms.find(params[:room_id])
    @plugs = @room.plugs.all
    render json: @rooms if stale?(etag: @rooms.all, last_modified: @plugs.maximum(:updated_at))
  end

	def create
	    @user=User.find(params[:user_id])
	    @room=@user.rooms.find(params[:room_id])
	    @plug= @room.plugs.create(plug_params)
	    @plug.user_id= @room.user_id
	    if @plug.save
	      render json: @plug, status: :created
	    else
	      render json: @plug.errors, status: :unprocessable_entity
	    end
	  end

	  def show
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @plug= @room.plugs.find(params[:plug_id])
    render json: @plug if stale?(@plug)
  end
end
